package com.woowahanrabbits.battle_people.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

	private static final String BEARER_PREFIX = "Bearer ";

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

		// 헤더 토큰 얻기
		String authorizationHeaders = headerAccessor.getFirstNativeHeader("Authorization");
		System.out.println(authorizationHeaders);
		// log.info(authorizationHeaders);
		// 토큰 자르기 fixme 토큰 자르는 로직 validate 로 리팩토링

		if (authorizationHeaders == null || authorizationHeaders.equals("null")) {
			throw new MessageDeliveryException("유저 토큰 없음");
		}

		// String token = authorizationHeaders.substring(BEARER_PREFIX.length());

		// System.out.println(token);

		return message;

	}
}
