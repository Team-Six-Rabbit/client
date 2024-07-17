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
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
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
	public void addBattle(BattleBoard battleBoard) {
		battleRepository.save(battleBoard);
	}

	@Override
	public List<Map<String, Object>> getRequestBattleList(String type, long userId, Pageable pageable) {
		Page<BattleBoard> page = null;
		if(type.equals("get")) {
			page = battleRepository.findByRegistUser_IdAndCurrentState(userId, 0, pageable);
		}
		if(type.equals("made")) {
			page = battleRepository.findByOppositeUser_IdAndCurrentState(userId, 0, pageable);
		}

		System.out.println(page.toList().toString());

		return page.stream().map(battleBoard -> {
			Map<String, Object> map = new HashMap<>();
			map.put("id", battleBoard.getId());
			map.put("regist_user", userRepository.findById(battleBoard.getRegistUser().getId()));
			map.put("opposite_user", userRepository.findById(battleBoard.getOppositeUser().getId()));
			map.put("min_people_count", battleBoard.getMinPeopleCount());
			map.put("max_people_count", battleBoard.getMaxPeopleCount());
			map.put("detail", battleBoard.getDetail());
			map.put("battle_rule", battleBoard.getBattleRule());
			map.put("regist_date", battleBoard.getRegistDate());
			map.put("current_state", battleBoard.getCurrentState());

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
		// List<BattleBoard> list = battleRepository.findAllLiveListByCurrentState(category);
		return List.of();
	}

	@Override
	public void updateBattleStatus(Long voteInfoId, String rejectionReason) {
		Long battle_id = battleRepository.findIdByVoteInfoId(voteInfoId);
		battleRepository.updateBattleBoardStatus(battle_id, rejectionReason);
	}

}
