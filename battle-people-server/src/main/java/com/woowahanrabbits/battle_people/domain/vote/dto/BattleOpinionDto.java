package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BattleOpinionDto {
	private final Integer index;
	private final String opinion;
	private final BasicUserDto user;
	private final Integer preCount;
	private final Integer finalCount;
	@Setter
	private Boolean isWinner;

	public BattleOpinionDto(VoteOpinion opinion) {
		this.index = opinion.getVoteOpinionIndex();
		this.opinion = opinion.getOpinion();
		this.user = new BasicUserDto(opinion.getUser());
		this.preCount = opinion.getPreCount();
		this.finalCount = opinion.getFinalCount();
	}
}
