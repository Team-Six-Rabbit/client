package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.BattleOpinionDto;

import lombok.Getter;

@Getter
public class BattleResponse {
	private final BattleDto battle;
	private final List<BattleOpinionDto> opinions;

	public BattleResponse(BattleDto battleDto, List<BattleOpinionDto> opinions) {
		this.battle = battleDto;
		this.opinions = opinions;
	}

	public BattleResponse(BattleBoard battleBoard, List<VoteOpinion> opinions) {
		this(new BattleDto(battleBoard), opinions.stream().map(BattleOpinionDto::new).toList());
	}
}
