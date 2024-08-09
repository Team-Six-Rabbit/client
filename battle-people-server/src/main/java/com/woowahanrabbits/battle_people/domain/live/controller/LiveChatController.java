package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;
import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;
	private final OpenViduService openViduService;
	private final RedisTemplate<String, Object> redisTemplate;

	private final SimpMessagingTemplate messagingTemplate;
	private final UserRepository userRepository;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto writeChatRequestDto) {
		String key = "chat";

		User user = userRepository.findById(1L).orElseThrow();
		user.setNickname("현치비");

		redisTemplate.convertAndSend(key, liveChatService.saveMessage(battleBoardId, writeChatRequestDto,
			user));
	}

	@MessageMapping("/request/{channel}")
	public void sendRequest(@DestinationVariable String channel) {
		Long battleBoardId = Long.parseLong(channel.split("-")[0]);
		Long userId = Long.parseLong(channel.split("-")[1]);

		String key = "request";
		// Redis에서 특정 키의 존재 여부 확인
		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
		if (valueOps.get(key + ":" + battleBoardId + ":" + userId) != null) {
			throw new RuntimeException("User with id " + userId + " has already sent a request.");
		}

		// 요청 저장
		valueOps.set(key + ":" + battleBoardId + ":" + userId, userId);

		//token 발급 받은 거 보내기
		redisTemplate.convertAndSend(key, openViduService.changeRole(battleBoardId, userId));
	}

}
