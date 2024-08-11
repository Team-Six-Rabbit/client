package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationType;
import com.woowahanrabbits.battle_people.domain.notify.service.NotifyService;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
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

	@Value("${min.people.count.value}")
	private Integer minPeopleCount;

	static Date date = new Date();

	//1. 상태코드 0인 battle 중 시작시간 30분 이전 거 상태코드 변환
	@Scheduled(cron = "0 * * * * *")
	public void appliedBattleEndedCheck() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 30);

		Date deadLineTimeCheck = calendar.getTime();
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(deadLineTimeCheck, 0);
		for (VoteInfo voteInfo : list) {
			voteInfo.setCurrentState(9);
		}
	}

	//todo: 2-2. 상태코드 2인 battle 중 시작시간 20분 이전 거 최소인원 수 넘었을 시 [이미지생성 + 상태코드 변환 + 표 취합] 매서드 실행
	@Scheduled(cron = "0 * * * * *")
	public void battleNotEnoughPeople() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 20);

		Date deadLineTimeCheck = calendar.getTime();
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(deadLineTimeCheck, 2);
		for (VoteInfo voteInfo : list) {
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());
			int currentPeopleCount = battleApplyUserRepository.countByBattleBoardId(battleBoard.getId());

			if (currentPeopleCount < minPeopleCount) {
				//밸런스 게임으로 이동
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(date);
				endDate.add(Calendar.DATE, 3);

				voteInfo.setStartDate(date);
				voteInfo.setEndDate(endDate.getTime());
				voteInfo.setCurrentState(5);
				voteInfoRepository.save(voteInfo);

			} else {

				//todo 썸네일 출력
				voteInfo.setCurrentState(3);
				voteScheduler.updatePreVoteCount(battleBoard);
				voteInfoRepository.save(voteInfo);
			}

		}
	}

	@Scheduled(cron = "0 * * * * *")
	public void changeLiveStatus() {
		List<VoteInfo> endLives = voteInfoRepository.findAllByEndDateAfterAndCurrentState(date, 4);
		for (VoteInfo voteInfo : endLives) {
			voteInfo.setCurrentState(8);
			voteScheduler.updateFinalVoteCount(voteInfo);
			voteInfoRepository.save(voteInfo);
		}

		List<VoteInfo> startLives = voteInfoRepository.findAllByStartDateAfterAndCurrentState(new Date(), 3);
		for (VoteInfo voteInfo : startLives) {
			voteInfo.setCurrentState(4);
			voteInfoRepository.save(voteInfo);
		}
	}

	@Scheduled(cron = "0 * * * * *")
	public void liveNotice() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 5);

		Date after5Mins = calendar.getTime();
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(after5Mins, 3);
		for (VoteInfo voteInfo : list) {
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());
			notifyService.sendNotification(battleBoard.getOppositeUser(), battleBoard,
				NotificationType.LIVE_NOTICE);
			notifyService.sendNotification(battleBoard.getRegistUser(), battleBoard,
				NotificationType.LIVE_NOTICE);

			List<BattleApplyUser> participants = battleApplyUserRepository.findByBattleBoardId(battleBoard.getId());
			for (BattleApplyUser battleApplyUser : participants) {
				notifyService.sendNotification(battleApplyUser.getUser(), battleBoard, NotificationType.LIVE_NOTICE);
			}

		}
	}

}
