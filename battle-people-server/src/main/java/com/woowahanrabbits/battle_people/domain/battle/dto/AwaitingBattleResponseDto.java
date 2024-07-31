package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwaitingBattleResponseDto {
	private final Long id;
	private final String title;
	private final List<VoteOpinionDto> opinionDtos;
	private final Date startDate;
	private final Date endDate;
	private final int maxPeopleCount;
	private final int currentPeopleCount;
	private boolean isVoted;

	public static AwaitingBattleResponseDto from(BattleBoard battleBoard, List<VoteOpinion> voteOpinions,
		int currentPeopleCount) {
		List<VoteOpinionDto> voteOpinionDtos = voteOpinions.stream()
			.map(VoteOpinionDto::new)
			.collect(Collectors.toList());

		return AwaitingBattleResponseDto.builder()
			.id(battleBoard.getId())
			.title(battleBoard.getVoteInfo().getTitle())
			.opinionDtos(voteOpinionDtos)
			.startDate(battleBoard.getVoteInfo().getStartDate())
			.endDate(battleBoard.getVoteInfo().getEndDate())
			.maxPeopleCount(battleBoard.getMaxPeopleCount())
			.currentPeopleCount(currentPeopleCount)
			.build();

	}
}
