package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;

/**
 * @deprecated
 * This class is deprecated and will be removed in a future release.
 * Use {@link BattleRespondRequest} instead.
 */

@Data
@Deprecated(forRemoval = true)
public class VoteDeclineDto {
	private Long battleId;
	private String rejectionReason;
}
