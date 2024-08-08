package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.Getter;
import lombok.Setter;

@Getter
public class VoteOpinionDtoWithVoteCount {
	private final Integer index;
	private final String opinion;
	@Setter
	private Integer count;
	@Setter
	private Integer percentage;

	public VoteOpinionDtoWithVoteCount(VoteOpinion vote) {
		this.index = vote.getVoteOpinionIndex();
		this.opinion = vote.getOpinion();
		this.percentage = 0;
		this.count = 0;
	}
}
