package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.Date;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveChatServiceImpl implements LiveChatService {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void saveMessage(WriteChatRequestDto chatDTO, String user) {
		String key = "live:" + chatDTO.getBattleBoardId();

		WriteChatResponseDto writeChatResponseDto = WriteChatResponseDto.builder()
			.userName(user)
			.message(chatDTO.getMessage())
			.regDate(new Date())
			.build();

		String message = "";
		try {
			message = objectMapper.writeValueAsString(writeChatResponseDto);
		} catch (Exception e) {
			throw new RuntimeException(e + ", mapping error");
		}

		redisTemplate.convertAndSend(key + ":chat", message);
		if (chatDTO.getHasRequest()) {
			redisTemplate.convertAndSend(key + ":request", message);
		}
	}
}
