package com.woowahanrabbits.battle_people.domain.live.dto.request;

import lombok.Data;

@Data
public class LiveBattleActionRequestDto<T> {
	private String type;
	private T data;
}
