package com.woowahanrabbits.battle_people.domain.notify.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.notify.domain.Notify;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationDetailResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationType;
import com.woowahanrabbits.battle_people.domain.notify.infrastructure.NotifyRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

	private final NotifyRepository notifyRepository;
	private final RedisTemplate<String, Object> redisTemplate;

	public void sendNotification(User user, BattleBoard battleBoard, NotificationType type) {

		String title = String.format(type.getMessageTemplate(),
			switch (type.getCode()) {
				case 0 -> battleBoard.getRegistUser().getNickname();
				case 1 -> battleBoard.getVoteInfo().getTitle();
				case 2 -> battleBoard.getVoteInfo().getTitle();
				case 3 -> battleBoard.getVoteInfo().getTitle();
				default -> throw new RuntimeException("Invalid notification type");
			}
		);

		Notify notify = new Notify();
		notify.setNotifyCode(NotificationType.BATTLE_REQUEST.getCode());
		notify.setTitle(title); // 완성된 제목을 설정
		notify.setUser(user);
		notify.setRegistDate(new Date());
		notify.setBattleBoard(battleBoard);
		notify.setRead(false);

		notifyRepository.save(notify);
		redisTemplate.convertAndSend("notify", new NotificationResponseDto(notify));
	}

	@Override
	public List<NotificationResponseDto> getNotifications(Long userId) {
		List<Notify> list = notifyRepository.findAllByUserIdAndIsReadFalseOrderByIsReadAscRegistDateDesc(userId);
		List<NotificationResponseDto> returnList = new ArrayList<>();
		for (Notify notify : list) {
			NotificationResponseDto notificationResponseDto = new NotificationResponseDto(notify);
			returnList.add(notificationResponseDto);
		}
		return returnList;
	}

	@Override
	public boolean hasUnreadNotifications(Long userId) {
		int size = notifyRepository.findAllByUserIdAndIsReadFalse(userId).size();
		return size > 0 ? true : false;
	}

	@Override
	public NotificationDetailResponseDto getNotificationDetail(Long notifyId) {
		Notify notify = notifyRepository.findById(notifyId).get();
		NotificationDetailResponseDto notificationDetailResponseDto = new NotificationDetailResponseDto();

		int notifyCode = notify.getNotifyCode();

		notificationDetailResponseDto.setId(notifyId);
		notificationDetailResponseDto.setNotifyCode(notifyCode);
		notificationDetailResponseDto.setTitle(notify.getTitle());

		Long battleBoardId = notify.getBattleBoard().getId();

		// if (notifyCode == 0) {
		// 	//배틀정보
		// 	BattleResponse battleResponse = battleService.getReceivedBattle(battleBoardId);
		// 	notificationDetailResponseDto.setSpecificData((BattleResponse)battleResponse);
		// } else if (notifyCode == 1) {
		// 	notificationDetailResponseDto.setSpecificData((Long)battleBoardId);
		// }

		notify.setRead(true);
		notifyRepository.save(notify);

		return notificationDetailResponseDto;
	}

	@Override
	public void deleteNotification(Long notifyId) {
		notifyRepository.deleteById(notifyId);
	}
}
