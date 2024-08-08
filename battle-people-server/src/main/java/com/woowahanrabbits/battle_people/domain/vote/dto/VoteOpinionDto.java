package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteOpinionDto {
	private Integer index;
	private String opinion;
	@Setter
	private Integer count;
	@Setter
	private Integer percentage;

	public VoteOpinionDto(VoteOpinion vote) {
		this.index = vote.getVoteOpinionIndex();
		this.opinion = vote.getOpinion();
		this.percentage = 0;
		this.count = 0;
	}
}
