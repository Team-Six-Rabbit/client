package com.woowahanrabbits.battle_people.domain.vote.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.config.AppProperties;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	private final VoteInfoRepository voteInfoRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

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

	@Scheduled(cron = "0 * * * * *")
	@Transactional
	@Override
	public void updateCurrentState() {
		Date now = new Date();
		System.out.println(now.toString());

		//밸런스게임 종료 후 결과 도출 로직
		List<VoteInfo> balanceGame = voteInfoRepository.findByEndDateBeforeAndCurrentState(now, 6);
		for (VoteInfo voteInfo : balanceGame) {
			voteInfo.setCurrentState(7);

			//vote Opinion 의 final count 계산 로직
			List<VoteOpinion> opinions = voteOpinionRepository.findByVoteInfoId(voteInfo.getId());
			for (int idx = 0; idx < 2; idx++) {
				int finalCnt = userVoteOpinionRepository.findByVoteInfoIdAndVoteInfoIndex(voteInfo.getId(), idx).size();
				opinions.get(idx).setFinalCount(finalCnt);
				voteOpinionRepository.save(opinions.get(idx));
			}

			voteInfoRepository.save(voteInfo);
		}

		//라이브 처리 로직
		Calendar liveStartCalendar = Calendar.getInstance();
		liveStartCalendar.setTime(now);
		liveStartCalendar.add(Calendar.MINUTE, 21);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, 3);

		List<VoteInfo> battle = voteInfoRepository.findByStartDateBeforeAndCurrentState(liveStartCalendar.getTime(), 2);
		for (VoteInfo voteInfo : battle) {
			//참여신청한 수 계산
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());
			int currentPeopleCount = battleApplyUserRepository.countByBattleBoardId(battleBoard.getId());

			if (currentPeopleCount >= appProperties.getMinPeopleCount()) {
				//썸네일 출력
				battleService.createThumbnail(battleBoard.getId());
				voteInfo.setCurrentState(3);
				voteInfoRepository.save(voteInfo);
			} else if (currentPeopleCount < appProperties.getMinPeopleCount()) {
				//밸런스 게임으로 이동
				voteInfo.setStartDate(now);
				voteInfo.setEndDate(calendar.getTime());
				voteInfo.setCurrentState(6);
				voteInfoRepository.save(voteInfo);
			}

		}

	}

}
