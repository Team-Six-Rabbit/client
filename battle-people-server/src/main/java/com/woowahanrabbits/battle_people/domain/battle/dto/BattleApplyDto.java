package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;

@Data
public class BattleApplyDto {
	private Long id;
	private Long userId;
	private int selectedOpinion;
}
