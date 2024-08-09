package com.woowahanrabbits.battle_people.domain.vote.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteScheduler {

	private final BattleBoardRepository battleBoardRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final VoteInfoRepository voteInfoRepository;

	@Scheduled(cron = "0 */5 * * * *")
	public void updatePreVoteCount() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.plusMinutes(15);
		LocalDateTime end = now.plusMinutes(20);

		List<BattleBoard> eligibleBattleBoards = battleBoardRepository.findEligibleBattleBoards(start, end);
		System.out.println("1: " + new Date());

		for (BattleBoard battleBoard : eligibleBattleBoards) {
			Long voteInfoId = battleBoard.getVoteInfo().getId();
			List<UserVoteOpinion> userVoteOpinionsOpt1 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
				voteInfoId, 0);
			List<UserVoteOpinion> userVoteOpinionsOpt2 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
				voteInfoId, 0);

			List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
			if (voteOpinions.size() < 2) {
				continue;
			}

			voteOpinions.get(0).setPreCount(userVoteOpinionsOpt1.size());
			voteOpinions.get(1).setPreCount(userVoteOpinionsOpt2.size());

			voteOpinionRepository.save(voteOpinions.get(0));
			voteOpinionRepository.save(voteOpinions.get(1));

			userVoteOpinionRepository.deleteByVoteInfoId(voteInfoId);

		}
	}

	@Scheduled(cron = "0 */5 * * * *")
	public void updateFinalVoteCount() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime end = now.plusMinutes(5);

		List<BattleBoard> eligibleBattleBoards = battleBoardRepository.findBattleBoardsByEndDate(now, end);

		System.out.println("2: " + new Date());
		for (BattleBoard battleBoard : eligibleBattleBoards) {
			Long voteInfoId = battleBoard.getVoteInfo().getId();
			List<UserVoteOpinion> userVoteOpinionsOpt1 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
				voteInfoId, 0);
			List<UserVoteOpinion> userVoteOpinionsOpt2 = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(
				voteInfoId, 0);

			List<VoteOpinion> voteOpinions = voteOpinionRepository.findByVoteInfoId(voteInfoId);
			if (voteOpinions.size() < 2) {
				continue;
			}

			voteOpinions.get(0).setFinalCount(userVoteOpinionsOpt1.size());
			voteOpinions.get(1).setFinalCount(userVoteOpinionsOpt2.size());

			voteOpinionRepository.save(voteOpinions.get(0));
			voteOpinionRepository.save(voteOpinions.get(1));

			userVoteOpinionRepository.deleteByVoteInfoId(voteInfoId);

		}
	}
}
