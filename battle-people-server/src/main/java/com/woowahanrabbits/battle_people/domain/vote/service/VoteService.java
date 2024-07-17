package com.woowahanrabbits.battle_people.domain.vote.service;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

public interface VoteService {
	void addVoteInfo(VoteInfo voteInfo);

	void addVoteOpinion(VoteOpinion voteOpinion);
}
