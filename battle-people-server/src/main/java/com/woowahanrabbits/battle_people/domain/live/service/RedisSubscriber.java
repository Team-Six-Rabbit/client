package com.woowahanrabbits.battle_people.domain.live.service;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;

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
			String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
			System.out.println(channel);
			System.out.println(publishMessage);
			publishMessage = publishMessage.substring(1, publishMessage.length() - 1);
			publishMessage = publishMessage.replaceAll("\\\\", "");
			System.out.println(publishMessage);
			Object object = objectMapper.readValue(publishMessage, WriteChatResponseDto.class);

			System.out.println(object);
			System.out.println(object.toString());

			WriteChatResponseDto chatMessage = objectMapper.readValue(publishMessage, WriteChatResponseDto.class);

			System.out.println(chatMessage.getUserName());
			System.out.println(chatMessage.getMessage());
			// Redis 채널 이름 읽기
			// String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
			String[] channels = channel.split(":");
			String battleId = channels[1];
			String type = channels[2];

			// 수신한 메시지 로깅
			log.info("Redis Subscribe Channel : " + battleId);
			log.info("Redis SUB Message : {}", chatMessage.getMessage());

			messagingTemplate.convertAndSend("/get/" + battleId, chatMessage);
		} catch (Exception e) {
			log.error("Error processing message", e);
		}
	}
}
