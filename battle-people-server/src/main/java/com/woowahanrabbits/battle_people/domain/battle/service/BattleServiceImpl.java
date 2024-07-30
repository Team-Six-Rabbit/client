package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

	private final BattleRepository battleRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final BattleApplyUserRepository battleApplyUserRepository;
	private final VoteInfoRepository voteInfoRepository;
	private final UserRepository userRepository;

	@Override
	public void registBattle(BattleInviteRequest battleInviteRequest, User user) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(battleInviteRequest.getStartDate());
		calendar.add(Calendar.MINUTE, battleInviteRequest.getTime());
		Date endDate = calendar.getTime();

		//VoteInfo 만들기
		VoteInfo voteInfo = VoteInfo.builder()
			.title(battleInviteRequest.getTitle())
			.startDate(battleInviteRequest.getStartDate())
			.endDate(endDate)
			.category(battleInviteRequest.getCategory())
			.build();

		//투표 정보 저장
		voteInfoRepository.save(voteInfo);

		//VoteOpinion 만들기
		System.out.println(voteInfo.getId());
		VoteOpinion voteOpinion = VoteOpinion.builder()
			.voteOpinionIndex(0)
			.voteInfoId(voteInfo.getId())
			.user(user)
			.opinion(battleInviteRequest.getOpinion())
			.build();
		voteOpinionRepository.save(voteOpinion);

		//battle board
		BattleBoard battleBoard = BattleBoard.builder()
			.registUser(user)
			.oppositeUser(userRepository.findById(battleInviteRequest.getOppositeUserId()).orElseThrow())
			.voteInfo(voteInfo)
			.maxPeopleCount(battleInviteRequest.getMaxPeopleCount())
			.detail(battleInviteRequest.getDetail())
			.currentState(0)
			.build();
		battleRepository.save(battleBoard);
	}

	// 	//배틀 등록
	// 	@Override
	// 	public void addBattle(BattleBoard battleBoard) {
	// 		battleRepository.save(battleBoard);
	// 	}
	//
	// 	//내가 요청한, 요청받은 배틀 리스트 조회
	// 	@Override
	// 	public Page<BattleReturnDto> getBattleList(String type, long userId, Pageable pageable) {
	// 		List<BattleBoard> page = battleRepository.findByUserIdAndType(userId, type, pageable).getContent();
	// 		List<BattleReturnDto> newList = new ArrayList<>();
	// 		for (BattleBoard battleBoard : page) {
	// 			BattleReturnDto battleReturnDto = new BattleReturnDto();
	// 			battleReturnDto.setBattle(battleBoard);
	// 			battleReturnDto.setOpinions(voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId()));
	// 			newList.add(battleReturnDto);
	// 		}
	// 		// System.out.println(page.toList().toString());
	// 		return new PageImpl<>(newList);
	// 	}
	//
	// 	//voteInfoId로 Battle가져오기
	// 	@Override
	// 	public BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId) {
	// 		return battleRepository.findByVoteInfoId(voteInfoId);
	// 	}
	//
	// 	@Override
	// 	public Page<?> getAwaitingBattleList(int category, Pageable pageable) {
	// 		List<BattleBoard> list = battleRepository.findByVoteInfo_CategoryAndCurrentState(category, 2, pageable)
	// 			.getContent();
	// 		List<BattleReturnDto> newList = new ArrayList<>();
	// 		for (BattleBoard battleBoard : list) {
	//
	// 			BattleReturnDto battleReturnDto = new BattleReturnDto();
	//
	// 			battleReturnDto.setBattle(battleBoard);
	// 			battleReturnDto.setOpinions(voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId()));
	// 			newList.add(battleReturnDto);
	// 		}
	// 		return new PageImpl<>(newList, pageable, list.size());
	// 	}
	//
	// 	//라이브에 참여 신청한 유저 리스트 리턴
	// 	@Override
	// 	public Page<?> getApplyUserList(Long battleBoardId, Pageable pageable) {
	// 		Page<BattleApplyUser> list = battleApplyUserRepository.findByBattleBoard_Id(battleBoardId, pageable);
	//
	// 		return list;
	// 	}
	//
	// 	//라이브 참여 신청한 유저 넣기
	// 	@Override
	// 	public void addBattleApplyUser(BattleApplyDto battleApplyDto) {
	// 		BattleApplyUser battleApplyUser = new BattleApplyUser();
	// 		BattleBoard battleBoard = new BattleBoard();
	// 		battleBoard.setId(battleApplyDto.getBattleId());
	// 		battleApplyUser.setBattleBoard(battleBoard);
	// 		User user = new User();
	// 		user.setId(battleApplyDto.getUserId());
	// 		battleApplyUser.setUser(user);
	// 		battleApplyUser.setSelectedOpinion(battleApplyDto.getSelectedOpinion());
	// 		battleApplyUserRepository.save(battleApplyUser);
	// 	}
	//
	// 	//rejectionReason 여부에 따라 battleBoard 내 currentState update
	// 	@Override
	// 	public void updateBattleStatus(Long battleId, String rejectionReason) {
	// 		battleRepository.updateBattleBoardStatus(battleId, rejectionReason);
	// 	}

}
