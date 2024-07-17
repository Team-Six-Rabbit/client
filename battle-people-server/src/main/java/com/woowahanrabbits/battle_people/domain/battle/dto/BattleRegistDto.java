package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleRegistDto {

	// BattleBoard 관련 필드
	private BattleBoard battleBoard;

	// VoteInfo 관련 필드
	private VoteInfo voteInfo;

	// Opinion 필드
	private String opinion;
}
