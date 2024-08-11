package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OpenViduMessageController {
	private final OpenViduService openViduService;
	private final RedisTemplate<String, Object> redisTemplate;

	// @MessageMapping("/live/{channel}")
	// public void sendUserVoteResult(@DestinationVariable String channel) {
	// 	Long battleBoardId = Long.parseLong(channel.split("-")[0]);
	// 	Long userId = Long.parseLong(channel.split("-")[1]);
	//
	// 	String key = "live";
	//
	// 	redisTemplate.convertAndSend(key, openViduService.changeRole(battleBoardId, userId));
	// }
}
