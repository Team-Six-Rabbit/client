package com.woowahanrabbits.battle_people.domain.live.service;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.live.dto.request.WriteChatRequestDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteChatResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.response.WriteTalkResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveChatServiceImpl implements LiveChatService {

	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final BattleRepository battleRepository;
	private static int chatIdx = 0;
	private static int requestIdx = 0;
	private final UserRepository userRepository;

	@Override
	public RedisTopicDto saveMessage(Long battleBoardId, WriteChatRequestDto writeChatRequestDto) {

		BasicUserDto user = new BasicUserDto(userRepository.findById(writeChatRequestDto.getUserId()).orElseThrow());

		WriteChatResponseDto writeChatResponseDto = WriteChatResponseDto.builder()
			.user(user)
			.message(writeChatRequestDto.getMessage())
			.build();

		VoteInfo voteInfo = battleRepository.findById(battleBoardId).orElseThrow().getVoteInfo();
		UserVoteOpinion userVoteOpinion = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(),
			voteInfo.getId());
		Integer userVote = (userVoteOpinion != null) ? userVoteOpinion.getVoteInfoIndex() : null;
		writeChatResponseDto.setUserVote(userVote);
		writeChatResponseDto.setIdx(chatIdx++);

		RedisTopicDto redisTopicDto = RedisTopicDto.builder()
			.battleBoardId(battleBoardId)
			.type("chat")
			.responseDto(writeChatResponseDto)
			.build();

		return redisTopicDto;

	}

	@Override
	public RedisTopicDto saveRequest(Long battleBoardId, User user) {

		VoteInfo voteInfo = battleRepository.findById(battleBoardId)
			.orElseThrow()
			.getVoteInfo();
		UserVoteOpinion userVoteOpinion = userVoteOpinionRepository.findByUserIdAndVoteInfoId(user.getId(),
			voteInfo.getId());
		if (userVoteOpinion == null) {
			throw new RuntimeException();
		}

		WriteTalkResponseDto writeTalkResponseDto = WriteTalkResponseDto.builder()
			.basicUserDto(new BasicUserDto(user))
			.idx(requestIdx++)
			.userVote(userVoteOpinion.getVoteInfoIndex())
			.build();

		RedisTopicDto redisTopicDto = RedisTopicDto.builder()
			.battleBoardId(battleBoardId)
			.type("request")
			.responseDto(writeTalkResponseDto)
			.build();

		return redisTopicDto;

	}

}
