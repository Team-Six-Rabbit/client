package com.woowahanrabbits.battle_people.domain.balancegame.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;

public interface BalanceGameService {
	void addBalanceGame(BattleReturnDto battleReturnDto);

	Page<?> getBalanceGameByConditions(int category, int status, Pageable pageable);

	void deleteBalanceGame(Long id);
}
