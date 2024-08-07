package com.woowahanrabbits.battle_people.domain.live.service;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
	private final ObjectMapper objectMapper;
	private final SimpMessageSendingOperations messagingTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			String publishMessage = new String(message.getBody(), StandardCharsets.UTF_8);

			RedisTopicDto<?> redisTopicDto = objectMapper.readValue(publishMessage, RedisTopicDto.class);

			Long battleBoardId = redisTopicDto.getBattleBoardId();
			String type = redisTopicDto.getType();
			if (type.equals("chat")) {
				RedisTopicDto<WriteChatResponseDto> chatTopicDto = objectMapper.readValue(publishMessage,
					new TypeReference<>() {
					});
				WriteChatResponseDto returnValue = chatTopicDto.getResponseDto();
				messagingTemplate.convertAndSend("/topic/chat/" + battleBoardId, returnValue);
			} else if (type.equals("request")) {
				RedisTopicDto<WriteTalkResponseDto> responseTopicDto = objectMapper.readValue(publishMessage,
					new TypeReference<>() {
					});
				WriteTalkResponseDto returnValue = responseTopicDto.getResponseDto();
				messagingTemplate.convertAndSend("/topic" + type + "/" + battleBoardId,
					returnValue);
			}

		} catch (Exception e) {
			log.error("Error processing message", e);
		}
	}
}
