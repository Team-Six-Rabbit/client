package com.woowahanrabbits.battle_people.domain.live.controller;

import java.util.LinkedHashMap;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.live.dto.request.LiveBattleActionRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteTalkRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;
import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteRequest;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;
	private final OpenViduService openViduService;
	private final VoteService voteService;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	private final SimpMessagingTemplate messagingTemplate;
	private final UserRepository userRepository;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto writeChatRequestDto) {
		String key = "chat";

		User user = userRepository.findById(1L).orElseThrow();
		user.setNickname("현치비");

		redisTemplate.convertAndSend(key, liveChatService.saveMessage(battleBoardId, writeChatRequestDto,
			user));
	}

	/*
		토론 방 전체 알림, key:live  app/live/{battleBoardId}, topic/live/{battleBoardId}
		1. 발언권 신청
			요청 메시지: {
				"type": "speak",
				"data": {
					@class = WriteTalkRequestDto
					"userId": 1
				}
			}
			발행: key: private /request/{channelId}, data: OpenViduTokenResponseDto

		2. 아이템 사용
			요청 메시지: {
				"type": "item",
				"data": {
					@class = ItemRequestDto
					"userId": 1,
					"itemCode": 1
				}
			}
			발행: key: live /live/{battleBoard}, data: UsedItemResponseDto

		3. 투표
			요청 메시지: {
				"type": "vote",
				"data": {
					"userId": 1,
					"voteInfoIndex": 0
				}
			}
			발행: key: live /live/{battleBoardId}, data: Live<VoteOpinionDto>
	*/
	@MessageMapping("/live/{battleBoardId}")
	public void sendLiveBattleAction(@DestinationVariable Long battleBoardId,
		LiveBattleActionRequestDto<?> liveBattleActionRequestDto) {
		String type = liveBattleActionRequestDto.getType();

		String key = "live";
		ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();

		if (type == null) {
			return;
		}
		if (type.equals("speak")) {
			sendRequestToRegisterOrOpposite(battleBoardId, valueOps, liveBattleActionRequestDto.getData());
		}
		if (type.equals("vote")) {
			System.out.println("vote");
			System.out.println(liveBattleActionRequestDto.getData());

			LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>)liveBattleActionRequestDto.getData();
			VoteRequest voteRequest = objectMapper.convertValue(map, VoteRequest.class);
			System.out.println(voteRequest);
			System.out.println(voteService.putLiveVote(battleBoardId, voteRequest));
			redisTemplate.convertAndSend("live",
				voteService.putLiveVote(battleBoardId, voteRequest));
		}

	}

	private void sendRequestToRegisterOrOpposite(Long battleBoardId, ValueOperations<String, Object> valueOps,
		Object data) {
		if (!data.getClass().equals(WriteTalkRequestDto.class)) {
			return;
		}
		String key = "private-request";
		Long userId = ((WriteTalkRequestDto)data).getUserId();

		if (valueOps.get(key + ":" + battleBoardId + ":" + userId) != null) {
			throw new RuntimeException("User with id " + userId + " has already sent a request.");
		}
		// 요청 저장
		valueOps.set(key + ":" + battleBoardId + ":" + userId, userId);
		User user = userRepository.findById(userId).orElse(null);
		redisTemplate.convertAndSend("live", liveChatService.saveRequest(battleBoardId, user));
	}

	// @MessageMapping("/request/{channel}")
	// public void sendRequest(@DestinationVariable String channel, WriteTalkRequestDto writeTalkRequestDto) {
	// 	Long battleBoardId = Long.parseLong(channel.split("-")[0]);
	// 	Long userId = Long.parseLong(channel.split("-")[1]);
	//
	// 	String key = "request";
	// 	ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
	//
	// 	// 요청이 들어왔을 때
	// 	if (Objects.equals(writeTalkRequestDto.getUserId(), userId)) {
	// 		// Redis에서 특정 키의 존재 여부 확인
	//
	// 		if (valueOps.get(key + ":" + battleBoardId + ":" + userId) != null) {
	// 			throw new RuntimeException("User with id " + userId + " has already sent a request.");
	// 		}
	// 		// 요청 저장
	// 		valueOps.set(key + ":" + battleBoardId + ":" + userId, userId);
	// 		User user = userRepository.findById(userId).orElse(null);
	// 		redisTemplate.convertAndSend(key, liveChatService.saveRequest(battleBoardId, user));
	// 		return;
	// 	}
	//
	// 	//token 발급 받은 거 보내기
	// 	if (valueOps.get(key + ":" + battleBoardId + ":" + userId) != null) {
	// 		valueOps.getOperations().delete(key + ":" + battleBoardId + ":" + userId);
	// 	}
	//
	// 	redisTemplate.convertAndSend(key, openViduService.changeRole(battleBoardId, userId));
	//
	// }

}
