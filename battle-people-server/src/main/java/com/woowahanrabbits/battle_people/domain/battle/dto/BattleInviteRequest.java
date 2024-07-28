package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BattleInviteRequest {
	@NotBlank
	private String title;
	@NotNull
	private String detail;
	@NotNull
	private Long startDate;
	@NotNull
	private Long endDate;
	@Range(min = 0, max = 7)
	private Integer category;

	@NotNull
	private Long oppositeUserId;
	@Size(min = 2, max = 2)
	private List<String> opinions;
	@Min(5)
	private Integer minPeopleCount;
	@Max(Long.MAX_VALUE)
	private Integer maxPeopleCount;
	private String battleRule;
}
