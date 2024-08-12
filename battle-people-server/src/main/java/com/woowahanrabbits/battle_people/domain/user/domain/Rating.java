package com.woowahanrabbits.battle_people.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rating {
	BALANCE_GAME_PARTICIPATE("밸런스게임", "참여", 5),
	BALANCE_GAME_WIN("밸런스게임", "승리", 10),
	LIVE_PARTICIPATE("라이브", "참여", 10),
	LIVE_WIN("라이브", "참여", 10),
	LIVE_LOSS("라이브", "참여", 10),
	LIVE_OWNER("라이브", "참여", 10);

	private final String session;
	private final String result;
	private final int point;

}
