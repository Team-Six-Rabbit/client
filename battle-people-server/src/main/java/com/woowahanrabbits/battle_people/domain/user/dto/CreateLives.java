package com.woowahanrabbits.battle_people.domain.user.dto;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateLives {
	private String title;
	private Date registDate;
	private Boolean isWin;

	public CreateLives(BattleBoard battleBoard, Boolean isWin) {
		this.title = battleBoard.getVoteInfo().getTitle();
		this.registDate = battleBoard.getRegistDate();
		this.isWin = isWin;
	}
}
