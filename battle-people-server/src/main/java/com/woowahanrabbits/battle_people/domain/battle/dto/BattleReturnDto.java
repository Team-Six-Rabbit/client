package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleReturnDto {

	// BattleBoard 관련 필드
	private BattleBoard battleBoard;

	// VoteInfo 관련 필드
	private List<VoteOpinion> opinionList;

}
