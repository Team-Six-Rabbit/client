package com.woowahanrabbits.battle_people.domain.notify.dto;

public enum NotificationType {
	BATTLE_REQUEST(0, "%s으로부터 도전장이 도착했습니다."),
	LIVE_NOTICE(1, "[%s] 라이브가 5분 후 시작됩니다!"),
	BATTLE_ACCEPT(2, "[%s]님이 배틀을 수락했습니다."),
	BATTLE_DECLINE(3, "[%s]님이 배틀을 거절했습니다."),
	BATTLE_UNSATISFIED(4, "[%s] 배틀 인원 미달로 모닥불로 이동되었습니다."),
	BATTLE_SATISFIED(5, "[%s] 배틀 인원이 충족되어 시작 대기 중입니다.");

	private final int code;
	private final String messageTemplate;

	NotificationType(int code, String messageTemplate) {
		this.code = code;
		this.messageTemplate = messageTemplate;
	}

	public int getCode() {
		return code;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}
}

