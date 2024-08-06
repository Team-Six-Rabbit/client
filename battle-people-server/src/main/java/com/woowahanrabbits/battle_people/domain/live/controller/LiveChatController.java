package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteTalkRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;

	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chat/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto chatDTO) {
		User user = new User();
		user.setId(3);
		user.setNickname("현치비");
		liveChatService.saveMessage(chatDTO, user);
	}

	@MessageMapping("/request/{battleBoardId}")
	public void sendRequest(@DestinationVariable Long battleBoardId, WriteTalkRequestDto writeTalkRequestDto) {
		User user = new User();
		user.setId(3);
		user.setNickname("현치비");
		liveChatService.saveRequest(writeTalkRequestDto, user);
		// liveChatService.saveMessage(chatDTO, user);
	}

	@GetMapping("/addTopicListener")
	public void addTopicListener(@RequestParam Long battleBoardId) {
		liveChatService.addTopicListener(battleBoardId);
	}

}
