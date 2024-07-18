package com.woowahanrabbits.battle_people.domain.battle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

public interface BattleService {
	void addBattle(BattleBoard battleBoard);

	Page<?> getBattleList(String type, long userId, Pageable pageable);

	void updateBattleStatus(Long battleId, String rejectionReason);

	BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId);

	Page<?> getAwaitingBattleList(int category, Pageable pageable);
}
