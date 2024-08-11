package com.woowahanrabbits.battle_people.domain.notify.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationDetailResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationType;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface NotifyService {

	void sendNotification(User user, BattleBoard battleBoard, NotificationType type);

	List<NotificationResponseDto> getNotifications(Long userId);

	boolean hasUnreadNotifications(Long userId);

	NotificationDetailResponseDto getNotificationDetail(Long notifyId);

	void deleteNotification(Long notifyId);
}
