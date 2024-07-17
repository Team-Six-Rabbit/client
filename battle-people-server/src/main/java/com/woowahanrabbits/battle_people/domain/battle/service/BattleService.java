package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;

public interface BattleService {
	public void registBattle(BattleRegistDto battleRegistDto);

	List<?> getRequestBattleList(String type, long userId);

	void acceptBattle(VoteOpinion voteOpinion, Long battleId);

	void declineBattle(String rejectionReason, Long battleId);

	List<?> getBattleList(String category);
}
