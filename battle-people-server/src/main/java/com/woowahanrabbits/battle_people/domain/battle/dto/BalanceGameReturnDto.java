package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;

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
	private int battleId;
	private String title;
	private int category;
	private Date startDate;
	private Date endDate;

}
