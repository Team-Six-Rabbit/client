package com.woowahanrabbits.battle_people.domain.balancegame.dto;

import com.woowahanrabbits.battle_people.domain.balancegame.domain.BalanceGameBoardComment;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;

import lombok.Getter;

@Getter
public class BalanceGameCommentResponse {
	private final Long id;
	private final Long battleId;

	private final BasicUserDto user;
	private final String content;
	private final Long registDate;

	public BalanceGameCommentResponse(BalanceGameBoardComment comment) {
		this.id = comment.getId();
		this.battleId = comment.getId();
		this.user = new BasicUserDto(comment.getUser());
		this.content = comment.getContent();
		this.registDate = comment.getRegistDate().getTime();
	}
}
