package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.List;

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
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveChatServiceImpl implements LiveChatService {

	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final BattleRepository battleRepository;
	private final VoteOpinionRepository voteOpinionRepository;
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
			.channelId(battleBoardId)
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

		List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfo.getId());

		WriteTalkResponseDto writeTalkResponseDto = WriteTalkResponseDto.builder()
			.hostUserId(voteOpinions.get(userVoteOpinion.getVoteInfoIndex()).getUser().getId())
			.requestUserId(user.getId())
			.idx(requestIdx++)
			.userVote(userVoteOpinion.getVoteInfoIndex())
			.build();

		RedisTopicDto redisTopicDto = RedisTopicDto.builder()
			.channelId(battleBoardId)
			.type("request")
			.responseDto(writeTalkResponseDto)
			.build();

		return redisTopicDto;

	}

}
