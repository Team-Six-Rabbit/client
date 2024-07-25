package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoteOpinionDto {
	private String opinion;
	private int voteCount;

	public VoteOpinionDto(VoteOpinion vote) {
		this.opinion = vote.getOpinion();
	}
}
