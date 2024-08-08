package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtUtil jwtUtil;

	private final SimpMessagingTemplate messagingTemplate;
	private final UserRepository userRepository;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto writeChatRequestDto,
		@Header("Authorization") String authorization) {
		System.out.println(authorization);
		// System.out.println(refresh);
		// BasicUserDto user = validateToken(access, refresh);

		String key = "chat";

		// 헤더에서 Authorization 토큰 얻기
		// System.out.println(authorization);
		// System.out.println(headerAccessor);
		// String authorizationHeader = headerAccessor.getFirstNativeHeader("Authorization");
		// System.out.println(authorizationHeader);

		User basicUserDto = userRepository.findById(7L).orElseThrow();
		// user.setNickname("현치비");
		BasicUserDto user = new BasicUserDto(basicUserDto);
		System.out.println(user.toString());

		redisTemplate.convertAndSend(key, liveChatService.saveMessage(battleBoardId, writeChatRequestDto,
			user));
	}

	private BasicUserDto validateToken(String access, String refresh) {
		Long userId = 0L;
		if (jwtUtil.validateToken(access, "access")) {
			userId = jwtUtil.extractUserId(access);
		} else if (jwtUtil.validateToken(refresh, "refresh")) {
			userId = jwtUtil.extractUserId(refresh);
		}
		BasicUserDto user = new BasicUserDto(userRepository.findById(userId).orElseThrow());

		return user;
	}

	@MessageMapping("/request/{battleBoardId}")
	public void sendRequest(@DestinationVariable Long battleBoardId) {
		String key = "chat";
		User user = userRepository.findById(7L).orElseThrow();
		user.setNickname("현치비");

		// Redis에서 특정 키의 존재 여부 확인
		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
		if (valueOps.get(key + ":" + battleBoardId + ":" + user.getId()) != null) {
			throw new RuntimeException("User with id " + user.getId() + " has already sent a request.");
		}

		// 요청 저장
		valueOps.set(key + ":" + battleBoardId + ":" + user.getId(), user.getId());

		redisTemplate.convertAndSend(key, liveChatService.saveRequest(battleBoardId, user));
	}

	@GetMapping("/addTopicListener")
	public void addTopicListener(@RequestParam Long battleBoardId) {
		liveChatService.addTopicListener(battleBoardId);
	}

}
