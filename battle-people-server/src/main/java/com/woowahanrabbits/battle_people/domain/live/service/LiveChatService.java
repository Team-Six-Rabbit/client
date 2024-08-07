package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteTalkRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface LiveChatService {
	WriteChatResponseDto saveMessage(WriteChatRequestDto chatDTO, User user);

	WriteTalkResponseDto saveRequest(WriteTalkRequestDto writeTalkRequestDto, User user);

	void addTopicListener(Long battleBoardId);
}
