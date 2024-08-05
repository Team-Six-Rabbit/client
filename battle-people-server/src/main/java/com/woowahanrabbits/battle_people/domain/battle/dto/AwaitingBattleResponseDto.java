package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;
import java.util.List;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.dto.BattleOpinionDto;

import lombok.Getter;

@Getter
public class AwaitingBattleResponseDto {
	private Long id;
	private String title;
	private List<BattleOpinionDto> opinionDtos;
	private Date startDate;
	private Date endDate;
	private int category;
	private int maxPeopleCount;
	private int userCount;
	private boolean isVoted;

	public AwaitingBattleResponseDto(VoteInfo voteInfo,
		List<BattleOpinionDto> battleOpinionDtos, int userCount, int maxPeopleCount, boolean isVoted) {
		this.id = voteInfo.getId();
		this.title = voteInfo.getTitle();
		this.opinionDtos = battleOpinionDtos;
		this.userCount = userCount;
		this.maxPeopleCount = maxPeopleCount;
		this.isVoted = isVoted;
		this.startDate = voteInfo.getStartDate();
		this.endDate = voteInfo.getEndDate();
		this.category = voteInfo.getCategory();
	}
}
