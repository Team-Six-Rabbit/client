package com.woowahanrabbits.battle_people.domain.live.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenViduTokenResponseDto {
	private String token;
	private int index;
}
