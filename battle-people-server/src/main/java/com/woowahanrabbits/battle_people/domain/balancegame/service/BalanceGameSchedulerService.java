package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteScheduler;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BalanceGameSchedulerService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteScheduler voteScheduler;

	@Scheduled(cron = "0 * * * * *")
	public void endBalanceGame() {
		List<VoteInfo> list = voteInfoRepository.findAllByEndDateAfterAndCurrentState(new Date(), 5);
		for (VoteInfo voteInfo : list) {
			voteScheduler.updateFinalVoteCount(voteInfo);
			voteInfo.setCurrentState(6);
		}
	}
}
