package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.Getter;
import lombok.Setter;

@Getter
public class VoteOpinionDto {
	private final Integer index;
	private final String opinion;
	@Setter
	private Integer finalCount;

	public VoteOpinionDto(VoteOpinion vote) {
		this.index = vote.getVoteOpinionIndex();
		this.opinion = vote.getOpinion();
		this.finalCount = vote.getFinalCount();
	}
}
