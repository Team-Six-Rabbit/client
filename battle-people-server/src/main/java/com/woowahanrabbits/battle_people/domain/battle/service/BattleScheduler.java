package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInfoDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.notify.infrastructure.NotifyRepository;
import com.woowahanrabbits.battle_people.domain.notify.service.NotifyService;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteScheduler;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BattleScheduler {

	private final VoteInfoRepository voteInfoRepository;
	private final BattleRepository battleRepository;
	private final BattleApplyUserRepository battleApplyUserRepository;
	private final VoteScheduler voteScheduler;
	private final NotifyService notifyService;
	private final NotifyRepository notifyRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final DalleService dalleService;
	private final UserService userService;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

	@Value("${min.people.count.value}")
	private Integer minPeopleCount;

	@Scheduled(cron = "0 * * * * *")
	public void checkLiveStatus() {

		//라이브 시작 상태 체크
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentStateLessThan(new Date(), 5);
		for (VoteInfo voteInfo : list) {
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());
			int currentPeopleCount = battleApplyUserRepository.countByBattleBoardId(battleBoard.getId());

			if (currentPeopleCount < minPeopleCount) {
				//todo 상대방 응답 없으면 폐기
				if (voteInfo.getCurrentState() == 0) {
					voteInfo.setCurrentState(9);
					voteInfoRepository.save(voteInfo);
				} else if (voteInfo.getCurrentState() == 2) {
					//todo 밸런스 게임으로 이동
					Calendar endDate = Calendar.getInstance();
					endDate.setTime(new Date());
					endDate.add(Calendar.DATE, 3);

					voteInfo.setStartDate(new Date());
					voteInfo.setEndDate(endDate.getTime());
					voteInfo.setCurrentState(5);
					voteInfoRepository.save(voteInfo);
				}
			} else {
				List<VoteOpinion> voteOpinions = voteOpinionRepository.findAllByVoteInfoId(voteInfo.getId());
				BattleInfoDto battleInfoDto = new BattleInfoDto(battleBoard, voteInfo, voteOpinions);
				if (battleBoard.getImageUrl() == null) {
					try {
						CompletableFuture<String> imageFuture = dalleService.generateImageAsync(battleInfoDto);
					} catch (Exception e) {
						// throw new RuntimeException(e);
					}
				}
				voteInfo.setCurrentState(3);
				voteScheduler.updatePreVoteCount(battleBoard);
				voteInfoRepository.save(voteInfo);
			}
		}

	}

	@Scheduled(cron = "0 * * * * *")
	public void endLiveStatus() {

		List<VoteInfo> endLives = voteInfoRepository.findAllByEndDateBeforeAndCurrentStateIn(new Date(),
			Arrays.asList(4, 5));

		for (VoteInfo voteInfo : endLives) {
			int result = voteScheduler.updateFinalVoteCount(voteInfo);
			voteInfo.setCurrentState(voteInfo.getCurrentState() == 4 ? 8 : 6);
			voteInfoRepository.save(voteInfo);
		}
	}

}
