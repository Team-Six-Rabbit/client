package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface LiveChatService {
	RedisTopicDto saveMessage(Long battleBoardId, WriteChatRequestDto writeChatRequestDto);

	RedisTopicDto saveRequest(Long battleBoardId, User user);

}
