package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;

@Data
public class BattleApplyDto {
	private Long battleId;
	/**
	 * @deprecated
	 * This method is deprecated due to security concerns, as it relies on trusting the userId provided in the input.
	 * For security reasons, use the user details obtained from SecurityContextHolder instead.
	 */
	@Deprecated(forRemoval = true)
	private Long userId;
	private int selectedOpinion;
}
