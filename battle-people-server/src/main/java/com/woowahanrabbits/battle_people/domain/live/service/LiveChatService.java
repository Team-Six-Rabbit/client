package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface LiveChatService {
	WriteChatResponseDto saveMessage(Long battleBoardId, WriteChatRequestDto writeChatRequestDto, User user);

	WriteTalkResponseDto saveRequest(Long battleBoardId, User user);

	void addTopicListener(Long battleBoardId);
}
