package com.woowahanrabbits.battle_people.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;
import com.woowahanrabbits.battle_people.domain.user.service.PrincipalDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

	private static final String BEARER_PREFIX = "Bearer ";
	private final JwtUtil jwtUtil;
	private final PrincipalDetailsService principalDetailsService;
	private final UserRepository userRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		System.out.println(channel);

		// 헤더에서 Authorization 토큰 얻기
		String authorizationHeader = headerAccessor.getFirstNativeHeader("Authorization");
		System.out.println(authorizationHeader);

		if (authorizationHeader == null || authorizationHeader.equals("null")) {
			throw new MessageDeliveryException("유저 토큰 없음");
		}

		// 토큰 분리
		String[] tokens = authorizationHeader.split(" ");
		Long userId = 0L;

		try {
			if (jwtUtil.validateToken(tokens[0], "access")) {
				userId = jwtUtil.extractUserId(tokens[0]);
			}

		} catch (Exception e) {
			if (jwtUtil.validateToken(tokens[1], "refresh")) {
				userId = jwtUtil.extractUserId(tokens[1]);
			} else {
				throw new MessageDeliveryException("토큰 인증 실패: " + e.getMessage());
			}
		}

		BasicUserDto basicUserDto = new BasicUserDto(userRepository.findById(userId).orElseThrow());
		headerAccessor.setHeader("user", basicUserDto);

		return message;
	}
}
