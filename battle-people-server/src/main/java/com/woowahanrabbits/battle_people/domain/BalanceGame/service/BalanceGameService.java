package com.woowahanrabbits.battle_people.domain.BalanceGame.service;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;

@Service
public interface BalanceGameService {
	void addBalanceGame(BattleReturnDto battleReturnDto);
}
