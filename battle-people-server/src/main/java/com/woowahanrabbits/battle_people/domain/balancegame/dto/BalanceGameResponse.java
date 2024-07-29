package com.woowahanrabbits.battle_people.domain.balancegame.dto;

import java.util.Date;
import java.util.List;

import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BalanceGameResponse {
	private Long id;        // 밸런스 게임 ID
	private String title;         // 투표 주제
	private String detail;
	private Date startDate; //시작일
	private Date endDate; //종료일
	private Integer category; //카테고리

	private List<VoteOpinionDto> opinions; //주장들
	private int currentState; //현재상태
	private Integer userVote; //유저의 선택
}
