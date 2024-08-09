package com.woowahanrabbits.battle_people.domain.notify.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.dto.BattleResponse;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.notify.domain.Notify;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationDetailResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationResponseDto;
import com.woowahanrabbits.battle_people.domain.notify.infrastructure.NotifyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

	private final NotifyRepository notifyRepository;
	private final BattleService battleService;

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

		if (notifyCode == 0) {
			//배틀정보
			BattleResponse battleResponse = battleService.getReceivedBattle(battleBoardId);
			notificationDetailResponseDto.setSpecificData((BattleResponse)battleResponse);
		} else if (notifyCode == 1) {
			notificationDetailResponseDto.setSpecificData((Long)battleBoardId);
		}

		notify.setRead(true);
		notifyRepository.save(notify);

		return notificationDetailResponseDto;
	}

	@Override
	public void deleteNotification(Long notifyId) {
		notifyRepository.deleteById(notifyId);
	}
}
