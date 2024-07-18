package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

public interface BattleService {
	void addBattle(BattleBoard battleBoard);

	Page<?> getBattleList(String type, long userId, Pageable pageable);

	List<?> getBattleList(String category);

	void updateBattleStatus(Long battleId, String rejectionReason);

	BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId);
}
