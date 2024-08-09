package com.woowahanrabbits.battle_people.domain.notify.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationDetailResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationResponseDto;

public interface NotifyService {
	List<NotificationResponseDto> getNotifications(Long userId);

	boolean hasUnreadNotifications(Long userId);

	NotificationDetailResponseDto getNotificationDetail(Long notifyId);

	void deleteNotification(Long notifyId);
}
