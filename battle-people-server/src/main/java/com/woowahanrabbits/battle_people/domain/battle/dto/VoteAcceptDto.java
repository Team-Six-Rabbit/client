package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;

@Data
public class VoteAcceptDto {
	private Long voteInfoId;
	private Long userId;
	private String opinion;
}
