package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;

public interface BattleService {
	void addBattle(BattleBoard battleBoard);

	Page<?> getBattleList(String type, long userId, int page);

	void updateBattleStatus(Long battleId, String rejectionReason);

	BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId);

	Page<?> getAwaitingBattleList(int category, int page);

	Page<BattleApplyUser> getApplyUserList(Long battleId, int page);

	void addBattleApplyUser(BattleApplyDto battleApplyDto);
}
