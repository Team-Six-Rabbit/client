package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;

public interface LiveChatService {
	void saveMessage(WriteChatRequestDto chatDTO, String user);
}
