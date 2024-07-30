package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface BattleService {
	void registBattle(BattleInviteRequest battleInviteRequest, User user);

	List<?> getRequestBattleList(String type, User user, int page);
	// void addBattle(BattleBoard battleBoard);
	//
	// Page<?> getBattleList(String type, long userId, Pageable pageable);
	//
	// void updateBattleStatus(Long battleId, String rejectionReason);
	//
	// BattleBoard getBattleBoardByVoteInfoId(Long voteInfoId);
	//
	// Page<?> getAwaitingBattleList(int category, Pageable pageable);
	//
	// Page<?> getApplyUserList(Long battleId, Pageable pageable);
	//
	// void addBattleApplyUser(BattleApplyDto battleApplyDto);
}
