package com.woowahanrabbits.battle_people.domain.live.service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.ItemRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.OpenViduTokenResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
	private final ObjectMapper objectMapper;
	private final SimpMessagingTemplate messagingTemplate;
	private final MessageConverter messageConverter;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
			String publishMessage = new String(message.getBody(), StandardCharsets.UTF_8);
			RedisTopicDto<?> redisTopicDto = objectMapper.readValue(publishMessage, RedisTopicDto.class);
			Long channelId = redisTopicDto.getChannelId();

			if (channel.equals("chat")) {
				Long battleBoardId = redisTopicDto.getChannelId();
				String type = redisTopicDto.getType();
				if (type.equals("chat")) {
					RedisTopicDto<WriteChatResponseDto> chatTopicDto = objectMapper.readValue(publishMessage,
						new TypeReference<>() {
						});
					WriteChatResponseDto returnValue = chatTopicDto.getResponseDto();
					messagingTemplate.convertAndSend("/topic/chat/" + battleBoardId, returnValue);
				}
			} else if (channel.equals("request")) {
				LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>)redisTopicDto.getResponseDto();
				OpenViduTokenResponseDto dto = objectMapper.convertValue(map, OpenViduTokenResponseDto.class);
				System.out.println("accept: " + dto);
				messagingTemplate.convertAndSend("/topic/request/" + channelId + "-" + dto.getUserId(), dto);

			} else if (channel.equals("live")) {
				if (redisTopicDto.getType().equals("item")) {
					LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>)redisTopicDto.getResponseDto();
					ItemRequestDto dto = objectMapper.convertValue(map, ItemRequestDto.class);
					System.out.println("item: " + dto);
					messagingTemplate.convertAndSend("/topic/live/" + channelId, dto);
				}
				if (redisTopicDto.getType().equals("request")) {
					LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>)redisTopicDto.getResponseDto();
					WriteTalkResponseDto returnValue = objectMapper.convertValue(map, WriteTalkResponseDto.class);
					messagingTemplate.convertAndSend(
						"/topic/live/" + channelId, returnValue);

				}
				if (redisTopicDto.getType().equals("vote")) {
					RedisTopicDto<List<?>> responseTopicDto = objectMapper.readValue(publishMessage,
						new TypeReference<>() {
						});
					messagingTemplate.convertAndSend("/topic/live/" + channelId,
						responseTopicDto.getResponseDto().get(1));
				}

			}

		} catch (Exception e) {
			log.error("Error processing message", e);
		}
	}
}
