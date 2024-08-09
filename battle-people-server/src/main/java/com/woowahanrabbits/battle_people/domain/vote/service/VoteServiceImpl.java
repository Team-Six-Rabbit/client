package com.woowahanrabbits.battle_people.domain.vote.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDtoWithVoteCount;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final BattleBoardRepository battleBoardRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final UserRepository userRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void addVoteInfo(VoteInfo voteInfo) {
		voteInfoRepository.save(voteInfo);
	}

	@Override
	public void addVoteOpinion(VoteOpinion voteOpinion) {
		voteOpinionRepository.save(voteOpinion);
	}

	@Override
	public CurrentVoteResponseDto putVoteOpinion(Long userId, Long battleBoardId, int voteInfoIndex) {
		Long voteInfoId = battleBoardRepository.findById(battleBoardId)
			.orElseThrow(() -> new RuntimeException("BattleBoard not found"))
			.getVoteInfo()
			.getId();

		UserVoteOpinion userVoteOpinion = userVoteOpinionRepository.findByUserIdAndVoteInfoId(userId, voteInfoId);
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		VoteInfo voteInfo = voteInfoRepository.findById(voteInfoId)
			.orElseThrow(() -> new RuntimeException("VoteInfo not found"));

		if (userVoteOpinion != null) {
			userVoteOpinion.setVoteInfoIndex(voteInfoIndex);
			userVoteOpinionRepository.save(userVoteOpinion);
		} else {
			userVoteOpinion = UserVoteOpinion.builder()
				.voteInfo(voteInfo)
				.user(user)
				.voteInfoIndex(voteInfoIndex)
				.build();
			userVoteOpinionRepository.save(userVoteOpinion);
		}

		CurrentVoteResponseDto responseDto = resultDto(voteInfoId);
		String channel = "voteResults-" + battleBoardId;
		try {
			redisTemplate.convertAndSend(channel, objectMapper.writeValueAsString(responseDto));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseDto;
	}

	@Override
	public CurrentVoteResponseDto getVoteResult(Long battleBoardId) {
		Long voteInfoId = battleBoardRepository.findById(battleBoardId)
			.orElseThrow(() -> new RuntimeException("BattleBoard not found"))
			.getVoteInfo()
			.getId();

		return resultDto(voteInfoId);
	}

	private CurrentVoteResponseDto resultDto(Long voteInfoId) {
		List<UserVoteOpinion> userVoteOpinionsOpt1 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
			voteInfoId, 0);
		List<UserVoteOpinion> userVoteOpinionsOpt2 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
			voteInfoId, 1);

		int voteCountOpt1 = userVoteOpinionsOpt1.size();
		int voteCountOpt2 = userVoteOpinionsOpt2.size();

		int totalCount = voteCountOpt1 + voteCountOpt2;

		if (totalCount == 0) {
			totalCount = 100;
		}

		List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
		List<VoteOpinionDtoWithVoteCount> opinions = new ArrayList<>();
		opinions.add(new VoteOpinionDtoWithVoteCount(0, voteOpinions.get(0).getOpinion(), voteCountOpt1,
			100 * voteCountOpt1 / totalCount));

		opinions.add(new VoteOpinionDtoWithVoteCount(1, voteOpinions.get(1).getOpinion(), voteCountOpt2,
			100 - (100 * voteCountOpt1 / totalCount)));

		return new CurrentVoteResponseDto(voteCountOpt1 + voteCountOpt2, opinions);
	}

}
