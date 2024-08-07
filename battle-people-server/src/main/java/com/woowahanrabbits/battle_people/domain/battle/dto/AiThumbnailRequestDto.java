package com.woowahanrabbits.battle_people.domain.battle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AiThumbnailRequestDto {
	private String prompt;
	private String size;
	@JsonProperty("n")
	private int number;
	private String quality;
	private String style;
}
