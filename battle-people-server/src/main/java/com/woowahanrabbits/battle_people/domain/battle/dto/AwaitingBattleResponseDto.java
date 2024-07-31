package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;
import java.util.List;

import com.woowahanrabbits.battle_people.domain.vote.dto.BattleOpinionDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.GetVoteInfoWithUserCountDto;

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
	private boolean isUserApplied;

	public AwaitingBattleResponseDto(GetVoteInfoWithUserCountDto getVoteInfoWithUserCountDto,
		List<BattleOpinionDto> battleOpinionDtos) {
		this.id = getVoteInfoWithUserCountDto.getId();
		this.title = getVoteInfoWithUserCountDto.getTitle();
		this.opinionDtos = battleOpinionDtos;
		this.startDate = getVoteInfoWithUserCountDto.getStartDate();
		this.endDate = getVoteInfoWithUserCountDto.getEndDate();
		this.category = getVoteInfoWithUserCountDto.getCategory();
		this.maxPeopleCount = getVoteInfoWithUserCountDto.getMaxPeopleCount();
		this.userCount = getVoteInfoWithUserCountDto.getUserCount();
		this.isUserApplied = getVoteInfoWithUserCountDto.isUserApplied();
	}
}
