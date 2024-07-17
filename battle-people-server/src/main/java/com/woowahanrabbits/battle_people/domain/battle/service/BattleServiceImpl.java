package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.VoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

	private final BattleRepository battleRepository;
	private final VoteInfoRepository voteInfoRepository;
	private final UserRepository userRepository;
	private final VoteOpinionRepository voteOpinionRepository;

	@Override
	public void registBattle(BattleRegistDto battleRegistDto) {
		// VoteInfo 엔티티를 생성하고 저장
		VoteInfo voteInfo = battleRegistDto.getVoteInfo();
		voteInfo = voteInfoRepository.save(voteInfo);

		// 저장된 VoteInfo의 ID를 BattleBoard에 설정
		Long voteInfoId = voteInfo.getId();
		BattleBoard battleBoard = battleRegistDto.getBattleBoard();
		battleBoard.setVoteInfoId(voteInfoId);

		//VoteOpinion에 본인의 주장 저장
		VoteOpinion voteOpinion = new VoteOpinion();
		voteOpinion.setVoteInfoId(voteInfoId);
		voteOpinion.setOpinion(battleRegistDto.getOpinion());
		voteOpinion.setUserId(battleBoard.getRegistUser().getId());
		voteOpinionRepository.save(voteOpinion);

		// BattleBoard 엔티티를 저장
		battleRepository.save(battleBoard);

		System.out.println(battleRegistDto.toString());
	}

	@Override
	public List<Map<String, Object>> getRequestBattleList(String type, long userId, Pageable pageable) {
		Page<BattleBoard> page = null;
		if(type.equals("get")) {
			page = battleRepository.findByRegistUserIdAndCurrentState(userId, 0, pageable);
		}
		if(type.equals("made")) {
			page = battleRepository.findByOppositeUserIdAndCurrentState(userId, 0, pageable);
		}

		return page.stream().map(battleBoard -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", battleBoard.getId());
			map.put("regist_user", userRepository.findById(battleBoard.getRegistUser().getId()));
			map.put("opposite_user", userRepository.findById(battleBoard.getOppositeUser().getId()));
			map.put("vote_info", voteInfoRepository.findById(battleBoard.getVoteInfoId()));
			map.put("min_people_count", battleBoard.getMinPeopleCount());
			map.put("max_people_count", battleBoard.getMaxPeopleCount());
			map.put("detail", battleBoard.getDetail());
			map.put("battle_rule", battleBoard.getBattleRule());
			map.put("regist_date", battleBoard.getRegistDate());
			map.put("current_state", battleBoard.getCurrentState());
			map.put("vote_opinion", voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfoId()));

			return map;
		}).collect(Collectors.toList());
	}

	@Override
	public void acceptBattle(VoteOpinion voteOpinion, Long battleId) {
		voteOpinion.setVoteOpinionIndex(1);
		voteOpinionRepository.save(voteOpinion);
		battleRepository.updateBattleBoardStatusTo2(battleId);
	}

	@Override
	public void declineBattle(String rejectionReason, Long battleId) {
		battleRepository.updateBattleBoardStatusAndRejectionReason(rejectionReason, battleId);
	}

	@Override
	public List<Map<String, Object>> getBattleList(String category) {
		List<BattleBoard> list = battleRepository.findAllLiveListByCurrentState(category);
		return List.of();
	}

}
