package com.woowahanrabbits.battle_people.domain.vote.service;

import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;

	@Override
	public void addVoteInfo(VoteInfo voteInfo) {
		voteInfoRepository.save(voteInfo);
	}

	@Override
	public void addVoteOpinion(VoteOpinion voteOpinion) {
		voteOpinionRepository.save(voteOpinion);
	}

}
