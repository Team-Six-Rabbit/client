package com.woowahanrabbits.battle_people.domain.vote.dto;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

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
public class BalanceGameVoteReturnDto {
	private Long battleId;        // 밸런스 게임 ID
	private String title;         // 투표 주제
	private String opinion1;      // 주장 1
	private String opinion2;      // 주장 2
	private int opinion1Count;    // 주장 1 투표수
	private int opinion2Count;    // 주장 2 투표수
	private Integer userVote;       // 유저가 투표한 주장
}
