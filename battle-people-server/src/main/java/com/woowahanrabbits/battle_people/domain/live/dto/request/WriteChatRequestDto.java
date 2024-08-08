package com.woowahanrabbits.battle_people.domain.live.dto.request;

import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;

import lombok.Data;

@Data
public class WriteChatRequestDto {
	BasicUserDto user;
	private String message;
}

