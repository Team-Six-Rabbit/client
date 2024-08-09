package com.woowahanrabbits.battle_people.domain.vote.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentVoteResponseDto {
	private Integer totalCount;
	private List<VoteOpinionDtoWithVoteCount> opinions;
}
