package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;
	private final RedisTemplate<String, Object> redisTemplate;

	private final SimpMessagingTemplate messagingTemplate;
	private final UserRepository userRepository;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto writeChatRequestDto) {
		String key = "live";

		User user = userRepository.findById(7L).orElseThrow();
		user.setNickname("현치비");

		redisTemplate.convertAndSend(key, liveChatService.saveMessage(battleBoardId, writeChatRequestDto,
			user));
	}

	@MessageMapping("/request/{battleBoardId}")
	public void sendRequest(@DestinationVariable Long battleBoardId) {
		String key = "live:" + battleBoardId + ":request";
		User user = userRepository.findById(7L).orElseThrow();
		user.setNickname("현치비");

		// Redis에서 특정 키의 존재 여부 확인
		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
		if (redisTemplate.hasKey(key)) {
			throw new RuntimeException("User with id " + user.getId() + " has already sent a request.");
		}
		// 요청 저장
		valueOps.set(key, user.getId());

		redisTemplate.convertAndSend(key, liveChatService.saveRequest(battleBoardId, user));
	}

	@GetMapping("/addTopicListener")
	public void addTopicListener(@RequestParam Long battleBoardId) {
		liveChatService.addTopicListener(battleBoardId);
	}

}
