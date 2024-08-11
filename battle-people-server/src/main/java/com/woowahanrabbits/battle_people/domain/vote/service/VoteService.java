package com.woowahanrabbits.battle_people.domain.vote.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.live.dto.RedisTopicDto;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.UserWinHistory;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDtoWithVoteCount;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteRequest;

public interface VoteService {
	void addVoteInfo(VoteInfo voteInfo);

	void addVoteOpinion(VoteOpinion voteOpinion);

	CurrentVoteResponseDto putVoteOpinion(Long userId, Long battleBoardId, int voteInfoIndex);

	CurrentVoteResponseDto getVoteResult(Long battleBoardId);

	RedisTopicDto<List<VoteOpinionDtoWithVoteCount>> putLiveVote(Long battleBoardId, VoteRequest voteRequest);

	UserWinHistory getUserWinHistory(Long userId);

	void updateCurrentState();

}
