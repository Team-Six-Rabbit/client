package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInfoDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.notify.dto.NotificationType;
import com.woowahanrabbits.battle_people.domain.notify.infrastructure.NotifyRepository;
import com.woowahanrabbits.battle_people.domain.notify.service.NotifyService;
import com.woowahanrabbits.battle_people.domain.user.domain.Rating;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
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
	private final UserRepository userRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final DalleService dalleService;
	private final UserService userService;
	private final UserVoteOpinionRepository userVoteOpinionRepository;

	@Value("${min.people.count.value}")
	private Integer minPeopleCount;

	//1. 상태코드 0인 battle 중 시작시간 30분 이전 거 상태코드 변환
	@Scheduled(cron = "0 * * * * *")
	public void appliedBattleEndedCheck() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 30);

		Date deadLineTimeCheck = calendar.getTime();
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(deadLineTimeCheck, 0);
		for (VoteInfo voteInfo : list) {
			voteInfo.setCurrentState(9);
			voteInfoRepository.save(voteInfo);
		}
	}

	//todo: 2-2. 상태코드 2인 battle 중 시작시간 20분 이전 거 최소인원 수 넘었을 시 [이미지생성 + 상태코드 변환 + 표 취합] 매서드 실행
	@Scheduled(cron = "0 * * * * *")
	public void battleNotEnoughPeople() {
		Date date = new Date();
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
				List<VoteOpinion> voteOpinions = voteOpinionRepository.findAllByVoteInfoId(voteInfo.getId());
				BattleInfoDto battleInfoDto = new BattleInfoDto(battleBoard, voteInfo, voteOpinions);
				try {
					CompletableFuture<String> imageFuture = dalleService.generateImageAsync(battleInfoDto);
				} catch (Exception e) {
					// throw new RuntimeException(e);
				}
				voteInfo.setCurrentState(3);
				voteScheduler.updatePreVoteCount(battleBoard);
				voteInfoRepository.save(voteInfo);
			}

		}
	}

	@Scheduled(cron = "0 * * * * *")
	public void changeLiveStatus() {
		Date date = new Date();
		List<VoteInfo> endLives = voteInfoRepository.findAllByEndDateBeforeAndCurrentState(date, 4);
		for (VoteInfo voteInfo : endLives) {
			voteInfo.setCurrentState(8);
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());
			int result = voteScheduler.updateFinalVoteCount(voteInfo);

			//투표 결과에 따른 포인트 지급
			List<UserVoteOpinion> voters = userVoteOpinionRepository.findByVoteInfoId(voteInfo.getId());
			if (result == -1) {
				for (UserVoteOpinion voter : voters) {
					userService.addPoint(voter.getUser(), battleBoard, Rating.LIVE_TIE);
				}
			} else {
				for (UserVoteOpinion voter : voters) {
					userService.addPoint(voter.getUser(), battleBoard,
						result == voter.getVoteInfoIndex() ? Rating.LIVE_WIN : Rating.LIVE_LOSS);
				}
			}

			//라이브 개최자
			userService.addPoint(battleBoard.getRegistUser(), battleBoard, Rating.LIVE_OWNER);
			userService.addPoint(battleBoard.getOppositeUser(), battleBoard, Rating.LIVE_OWNER);

			voteInfoRepository.save(voteInfo);
		}

		List<VoteInfo> startLives = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(new Date(), 3);
		for (VoteInfo voteInfo : startLives) {
			voteInfo.setCurrentState(4);
			voteInfoRepository.save(voteInfo);
			BattleBoard battleBoard = battleRepository.findByVoteInfoId(voteInfo.getId());

			//사전참여자에게 10포인트
			List<BattleApplyUser> participator = battleApplyUserRepository.findByBattleBoardId(battleBoard.getId());
			for (BattleApplyUser battleApplyUser : participator) {
				userService.addPoint(battleApplyUser.getUser(), battleBoard, Rating.LIVE_PARTICIPATE);
			}

		}
	}

	@Scheduled(cron = "0 * * * * *")
	public void liveNotice() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 5);

		Date after5Mins = calendar.getTime();
		List<VoteInfo> list = voteInfoRepository.findAllByStartDateBeforeAndCurrentState(after5Mins, 3);
		for (VoteInfo voteInfo : list) {
			if (notifyRepository.existsByBattleBoardIdAndNotifyCode(
				battleRepository.findByVoteInfoId(voteInfo.getId()).getId(), 1)) {
				continue;
			}
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

	//point
	public void addPoint(User user, BattleBoard battleBoard, Rating rating) {
		int rate = user.getRating();
		rate += rating.getPoint();
		user.setRating(rate);
		userRepository.save(user);
		notifyService.sendPointNotification(user, battleBoard, NotificationType.ADD_POINT, rating);
	}

}
