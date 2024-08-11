package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceGameSchedulerService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

	//todo: 종료시간 된 밸런스게임 투표율 갱신 및 상태코드 변환
	@Transactional
	@Scheduled(cron = "0 * * * * *")
	public void endBalanceGame() {
		List<VoteInfo> list = voteInfoRepository.findAllByEndDateAfterAndCurrentState(new Date(), 5);
		for (VoteInfo voteInfo : list) {
			List<VoteOpinion> voteOpinions = voteOpinionRepository.findAllByVoteInfoId(voteInfo.getId());
			for (VoteOpinion voteOpinion : voteOpinions) {
				int finalCount = userVoteOpinionRepository.countByVoteInfoIdAndVoteInfoIndex(voteInfo.getId(),
					voteOpinion.getVoteOpinionIndex());
				voteOpinion.setFinalCount(finalCount);
				voteOpinionRepository.save(voteOpinion);
			}
			voteInfo.setCurrentState(6);
		}
	}
}
