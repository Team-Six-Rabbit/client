package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;

@Data
public class VoteDeclineDto {
	private Long battleId;
	private String rejectionReason;
}
