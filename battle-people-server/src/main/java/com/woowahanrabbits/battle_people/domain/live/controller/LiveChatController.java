package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;

	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/{battleBoardId}")
	public void sendMessage(@DestinationVariable Long battleBoardId, WriteChatRequestDto chatDTO) {
		liveChatService.saveMessage(chatDTO, new String("user"));
	}

}
