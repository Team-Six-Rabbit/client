package com.woowahanrabbits.battle_people.domain.balancegame.dto;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BalanceGameCommentDto {

	private Long id;
	private Long battleBoardId;
	private User user;
	private String content;
	private Date registDate;
}
