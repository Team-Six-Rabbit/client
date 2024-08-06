package com.woowahanrabbits.battle_people.domain.live.dto.request;

import lombok.Data;

@Data
public class WriteTalkRequestDto {
	private Long battleBoardId;
	private int opinion;
}
