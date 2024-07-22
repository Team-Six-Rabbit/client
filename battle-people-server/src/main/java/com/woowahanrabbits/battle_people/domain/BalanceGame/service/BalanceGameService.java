package com.woowahanrabbits.battle_people.domain.balancegame.service;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface BalanceGameService {
	void addBalanceGame(BattleReturnDto battleReturnDto);

	Object getBalanceGameByConditions(int category, int status, int page, User user);

	void deleteBalanceGame(Long id);
}
