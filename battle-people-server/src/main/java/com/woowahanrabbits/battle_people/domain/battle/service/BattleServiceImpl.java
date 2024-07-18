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


	//배틀 등록
	@Override
	public void addBattle(BattleBoard battleBoard) {
		battleRepository.save(battleBoard);
	}

	//내가 요청한, 요청받은 배틀 리스트 조회
	@Override
	public Page<BattleBoard> getBattleList(String type, long userId, Pageable pageable) {
		Page<BattleBoard> page = battleRepository.findByUserIdAndType(userId, type, pageable);
		// System.out.println(page.toList().toString());
		return page;
	}


	//voteInfoId로 Battle가져오기
	@Override
	public BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId) {
		return battleRepository.findByVoteInfoId(voteInfoId);
	}

	//rejectionReason 여부에 따라 battleBoard 내 currentState update
	@Override
	public void updateBattleStatus(Long battleId, String rejectionReason) {
		battleRepository.updateBattleBoardStatus(battleId, rejectionReason);
	}

}
