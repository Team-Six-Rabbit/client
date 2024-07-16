package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleRegistDto {

	// BattleBoard 관련 필드
	private Long registUserId;
	private Long oppositeUserId;
	private int minPeopleCount;
	private int maxPeopleCount;
	private String detail;
	private String battleRule;

	// VoteInfo 관련 필드
	private String title;
	private Date startDate;
	private Date endDate;
	private int category;

	// Opinion 필드
	private String opinion;
}
