package com.woowahanrabbits.battle_people.domain.vote.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.config.AppProperties;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.UserWinHistory;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDtoWithVoteCount;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteRequest;
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
	private final BattleRepository battleRepository;
	private final BattleApplyUserRepository battleApplyUserRepository;
	private final BattleService battleService;
	private final AppProperties appProperties;

	@Override
	public void addVoteInfo(VoteInfo voteInfo) {
		voteInfoRepository.save(voteInfo);
	}

	@Override
	public void addVoteOpinion(VoteOpinion voteOpinion) {
		voteOpinionRepository.save(voteOpinion);
	}

	@Override
	public CurrentVoteResponseDto putVoteOpinion(Long userId, Long voteInfoId, int voteInfoIndex) {
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

	@Override
	public RedisTopicDto<List<VoteOpinionDtoWithVoteCount>> putLiveVote(Long battleBoardId, VoteRequest voteRequest) {
		BattleBoard battleBoard = battleBoardRepository.findById(battleBoardId).orElse(null);

		if (battleBoard == null) {
			return null;
		}

		CurrentVoteResponseDto currentVoteResponseDto = putVoteOpinion(voteRequest.getUserId(),
			battleBoard.getVoteInfo().getId(), voteRequest.getVoteInfoIndex());

		RedisTopicDto redisTopicDto = RedisTopicDto.builder()
			.channelId(battleBoardId)
			.type("vote")
			.responseDto(currentVoteResponseDto.getOpinions())
			.build();

		return redisTopicDto;
	}

	@Override
	public UserWinHistory getUserWinHistory(Long userId) {
		List<UserVoteOpinion> userVoteHistory = userVoteOpinionRepository.findByUserId(userId);
		int totalUserVoteCount = 0;
		int winCount = 0;

		for (UserVoteOpinion userVoteOpinion : userVoteHistory) {
			List<VoteOpinion> opinions = voteOpinionRepository.findByVoteInfoId(userVoteOpinion.getVoteInfoId());
			int selectIndex = userVoteOpinion.getVoteInfoIndex();

			if (opinions.size() < 2 || opinions.get(selectIndex).getFinalCount() == null
				|| opinions.get(1 - selectIndex).getFinalCount() == null) {
				continue;
			}

			if (opinions.get(selectIndex).getFinalCount() > opinions.get(1 - selectIndex).getFinalCount()) {
				winCount++;
			}
			totalUserVoteCount++;
		}

		if (totalUserVoteCount == 0) {
			return new UserWinHistory(0, 0, 0, 0);
		}

		return UserWinHistory.builder()
			.debateCnt(totalUserVoteCount)
			.winCnt(winCount)
			.loseCnt(totalUserVoteCount - winCount)
			.winRate(100 * winCount / totalUserVoteCount)
			.build();
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

	@Override
	public CurrentVoteResponseDto getVoteResultByVoteInfoId(Long voteInfoId) {
		return resultDto(voteInfoId);
	}

}
