package com.woowahanrabbits.battle_people.domain.live.service;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;

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
			System.out.println(publishMessage);
			String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
			String[] channels = channel.split(":");
			String battleId = channels[1];
			String type = channels[3];

			if (type.equals("chat")) {
				WriteChatResponseDto chatMessage = objectMapper.readValue(publishMessage, WriteChatResponseDto.class);
				messagingTemplate.convertAndSend("/topic/" + type + "/" + battleId, chatMessage);
			} else if (type.equals("request")) {
				WriteTalkResponseDto user = objectMapper.readValue(publishMessage, WriteTalkResponseDto.class);
				messagingTemplate.convertAndSend("/topic/" + type + "/" + battleId, user);
			} else if (type.equals("vote")) {
				System.out.println("vote");
				CurrentVoteResponseDto result = objectMapper.readValue(publishMessage, CurrentVoteResponseDto.class);
				messagingTemplate.convertAndSend("/topic/" + type + "/" + battleId, result);
			}

			// 수신한 메시지 로깅
			log.info("Redis Subscribe Channel : " + battleId);
			// log.info("Redis SUB Message : {}", chatMessage.getMessage());

		} catch (Exception e) {
			log.error("Error processing message", e);
		}
	}
}
