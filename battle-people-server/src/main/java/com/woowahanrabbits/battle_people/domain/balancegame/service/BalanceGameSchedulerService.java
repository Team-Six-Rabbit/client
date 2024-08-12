package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.user.service.UserService;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteScheduler;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BalanceGameSchedulerService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteScheduler voteScheduler;
	private final UserService userService;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

	@Scheduled(cron = "0 * * * * *")
	public void endBalanceGame() {
		List<VoteInfo> list = voteInfoRepository.findAllByEndDateBeforeAndCurrentState(new Date(), 5);
		for (VoteInfo voteInfo : list) {
			int result = voteScheduler.updateFinalVoteCount(voteInfo);
			voteInfo.setCurrentState(6);
			voteInfoRepository.save(voteInfo);

			List<UserVoteOpinion> voters = userVoteOpinionRepository.findByVoteInfoId(voteInfo.getId());
			for (UserVoteOpinion voter : voters) {

			}
		}
	}
}
