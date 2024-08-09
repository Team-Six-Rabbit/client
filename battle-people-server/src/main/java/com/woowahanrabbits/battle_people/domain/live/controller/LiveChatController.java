package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;
	private final RedisTemplate<String, Object> redisTemplate;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto writeChatRequestDto) {
		String key = "chat";
		redisTemplate.convertAndSend(key, liveChatService.saveMessage(battleBoardId, writeChatRequestDto));
	}

	@MessageMapping("/request/{battleBoardId}")
	public void sendRequest(@DestinationVariable Long battleBoardId, Long userId) {
		String key = "chat";
		//리스트에 추가하는 로직
		// redisTemplate.opsForList().leftPush(key+":request")

		//알림을 보내는 로직
	}

}
