package com.woowahanrabbits.battle_people.domain.live.service;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteTalkRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveChatServiceImpl implements LiveChatService {

	private final ObjectMapper objectMapper;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final BattleRepository battleRepository;
	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final MessageListenerAdapter messageListenerAdapter;

	@Override
	public WriteChatResponseDto saveMessage(WriteChatRequestDto chatDTO, User user) {

		WriteChatResponseDto writeChatResponseDto = WriteChatResponseDto.builder()
			.userName(user.getNickname())
			.message(chatDTO.getMessage())
			// .regDate(new Date())
			.build();

		VoteInfo voteInfo = battleRepository.findById(chatDTO.getBattleBoardId()).orElseThrow().getVoteInfo();
		UserVoteOpinion userVoteOpinion = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(),
			voteInfo.getId());
		Integer userVote = (userVoteOpinion != null) ? userVoteOpinion.getVoteInfoIndex() : null;
		writeChatResponseDto.setUserVote(userVote);

		// String message = "";
		// try {
		// 	message = objectMapper.writeValueAsString(writeChatResponseDto);
		// } catch (Exception e) {
		// 	throw new RuntimeException(e + ", mapping error");
		// }
		return writeChatResponseDto;
	}

	@Override
	public WriteTalkResponseDto saveRequest(WriteTalkRequestDto writeTalkRequestDto, User user) {

		WriteTalkResponseDto writeTalkResponseDto = WriteTalkResponseDto.builder()
			.userId(user.getId())
			.userNickname(user.getNickname())
			.build();

		VoteInfo voteInfo = battleRepository.findById(writeTalkRequestDto.getBattleBoardId())
			.orElseThrow()
			.getVoteInfo();
		UserVoteOpinion userVoteOpinion = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(),
			voteInfo.getId());
		if (userVoteOpinion == null) {
			throw new RuntimeException();
		}
		writeTalkResponseDto.setUserVote(userVoteOpinion.getVoteInfoIndex());

		return writeTalkResponseDto;

	}

	@Override
	public void addTopicListener(Long battleBoardId) {
		ChannelTopic chatTopic = new ChannelTopic("live:" + battleBoardId + ":chat");
		ChannelTopic requestTopic = new ChannelTopic("live:" + battleBoardId + ":request");
		ChannelTopic voteTopic = new ChannelTopic("live:" + battleBoardId + ":vote");
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, chatTopic);
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, requestTopic);
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, voteTopic);
	}
}
