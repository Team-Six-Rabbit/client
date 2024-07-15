package com.woowahanrabbits.battle_people.domain.battle.service;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;

@Service
public interface BattleService {
	public void registBattle(BattleRegistDto battleRegistDto);
}
