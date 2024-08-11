package com.woowahanrabbits.battle_people.domain.notify.dto;

public enum NotificationType {
	BATTLE_REQUEST(0, "%s님이 배틀을 신청했어요! 지금 바로 확인해보세요."),
	LIVE_NOTICE(1, "[%s] 라이브 시작 5분 전입니다!"),
	BATTLE_ACCEPT(2, "[%s] 배틀이 승낙되어 대기중입니다."),
	BATTLE_DECLINE(3, "[%s] 배틀이 거절되었습니다.");
	//todo 인원 수 미달 -> 모닥불 (참여자, 개최자)
	//todo 인원 수 충족 -> 라이브 개최 (참여자, 개최자)

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

