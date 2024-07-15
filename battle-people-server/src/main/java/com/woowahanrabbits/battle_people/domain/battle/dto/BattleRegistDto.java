package com.woowahanrabbits.battle_people.domain.battle.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
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
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int category;

	// Opinion 필드
	private String opinion;
}
