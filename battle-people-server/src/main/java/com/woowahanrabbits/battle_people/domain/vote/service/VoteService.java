package com.woowahanrabbits.battle_people.domain.vote.service;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;

public interface VoteService {
	void addVoteInfo(VoteInfo voteInfo);

	void addVoteOpinion(VoteOpinion voteOpinion);

	CurrentVoteResponseDto putVoteOpinion(Long userId, Long battleBoardId, int voteInfoIndex);

	CurrentVoteResponseDto getVoteResult(Long battleBoardId);
}
