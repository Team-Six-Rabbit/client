package com.woowahanrabbits.battle_people.domain.live.service;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
		System.out.println("7");
		try {
			System.out.println("8");
			String publishMessage = new String(message.getBody(), StandardCharsets.UTF_8);
			System.out.println(publishMessage);
			String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
			String[] channels = channel.split(":");
			String battleId = channels[1];
			String type = channels[2];

			log.info("Received message on channel: {}", channel);
			log.info("Published message: {}", publishMessage);

			if (type.equals("chat")) {
				WriteChatResponseDto chatMessage = objectMapper.readValue(publishMessage, WriteChatResponseDto.class);
				messagingTemplate.convertAndSend("/topic/" + type + "/" + battleId, chatMessage);
				log.info("Sent chat message to /topic/{}/{}", type, battleId);
			} else if (type.equals("request")) {
				WriteTalkResponseDto user = objectMapper.readValue(publishMessage, WriteTalkResponseDto.class);
				messagingTemplate.convertAndSend("/topic/" + type + "/" + battleId, user);
				log.info("Sent request message to /topic/{}/{}", type, battleId);
			}

		} catch (Exception e) {
			log.error("Error processing message", e);
		}
	}
}
