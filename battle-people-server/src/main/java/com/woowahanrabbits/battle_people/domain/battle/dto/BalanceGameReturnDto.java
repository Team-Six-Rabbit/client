package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;
import java.util.List;

import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDto;

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
public class BalanceGameReturnDto {
	private Long battleId;        // 밸런스 게임 ID
	private String title;         // 투표 주제
	private List<VoteOpinionDto> opinions; //주장들
	private Date startDate; //시작일
	private Date endDate; //종료일
}
