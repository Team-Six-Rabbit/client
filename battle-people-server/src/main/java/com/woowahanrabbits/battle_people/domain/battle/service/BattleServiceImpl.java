package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

	private final BattleRepository battleRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final BattleApplyUserRepository battleApplyUserRepository;

	//배틀 등록
	@Override
	public void addBattle(BattleBoard battleBoard) {
		battleRepository.save(battleBoard);
	}

	//내가 요청한, 요청받은 배틀 리스트 조회
	@Override
	public Page<BattleReturnDto> getBattleList(String type, long userId, int page) {
		List<BattleBoard> list = battleRepository.findByUserIdAndType(userId, type);
		List<BattleReturnDto> newList = new ArrayList<>();

		for(BattleBoard battleBoard : list) {
			BattleReturnDto battleReturnDto = new BattleReturnDto();
			battleReturnDto.setBattleBoard(battleBoard);
			battleReturnDto.setOpinionList(voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId()));
			newList.add(battleReturnDto);
		}

		Pageable pageable = PageRequest.of(page, 12);
		return new PageImpl<>(newList, pageable, list.size());
	}


	//voteInfoId로 Battle가져오기
	@Override
	public BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId) {
		return battleRepository.findByVoteInfoId(voteInfoId);
	}

	@Override
	public Page<?> getAwaitingBattleList(int category, int page) {
		List<BattleBoard> list = battleRepository.findByVoteInfo_CategoryAndCurrentState(category, 2);

		List<BattleReturnDto> newList = new ArrayList<>();
		for(BattleBoard battleBoard : list) {

			BattleReturnDto battleReturnDto = new BattleReturnDto();
			battleReturnDto.setBattleBoard(battleBoard);
			battleReturnDto.setOpinionList(voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId()));
			newList.add(battleReturnDto);
		}

		Pageable pageable = PageRequest.of(page, 12);
		return new PageImpl<>(newList, pageable, list.size());
	}

	//라이브에 참여 신청한 유저 리스트 리턴
	@Override
	public Page<BattleApplyUser> getApplyUserList(Long battleBoardId, int page) {
		List<BattleApplyUser> list = battleApplyUserRepository.findByBattleBoard_Id(battleBoardId);
		Pageable pageable = PageRequest.of(page, 12);
		return new PageImpl<BattleApplyUser>(list, pageable, list.size());
	}

	//라이브 참여 신청한 유저 넣기
	@Override
	public void addBattleApplyUser(BattleApplyDto battleApplyDto) {
		BattleApplyUser battleApplyUser = new BattleApplyUser();
		BattleBoard battleBoard	= new BattleBoard();
		battleBoard.setId(battleApplyDto.getBattleId());
		battleApplyUser.setBattleBoard(battleBoard);
		User user = new User();
		user.setId(battleApplyDto.getUserId());
		battleApplyUser.setUser(user);
		battleApplyUser.setSelectedOpinion(battleApplyDto.getSelectedOpinion());
		battleApplyUserRepository.save(battleApplyUser);
	}

	//rejectionReason 여부에 따라 battleBoard 내 currentState update
	@Override
	public void updateBattleStatus(Long battleId, String rejectionReason) {
		battleRepository.updateBattleBoardStatus(battleId, rejectionReason);
	}

}
