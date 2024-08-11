package com.woowahanrabbits.battle_people.domain.battle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AiThumbnailRequestDto {
	private String prompt;
	private String model;
	private String size;
	@JsonProperty("n")
	private int number;
	private String quality;
	private String style;
}
