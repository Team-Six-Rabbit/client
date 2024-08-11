package com.woowahanrabbits.battle_people.domain.user.dto;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateLives {
	private final Long battleBoardId;
	private final String title;
	private final Date registDate;
	private final Boolean isWin;

	public CreateLives(BattleBoard battleBoard, Boolean isWin) {
		this.battleBoardId = battleBoard.getId();
		this.title = battleBoard.getVoteInfo().getTitle();
		this.registDate = battleBoard.getRegistDate();
		this.isWin = isWin;
	}
}
