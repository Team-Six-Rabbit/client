// package com.woowahanrabbits.battle_people.domain.battle.service;
//
// import java.util.Calendar;
// import java.util.Date;
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
//
// import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
// import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
// import com.woowahanrabbits.battle_people.domain.battle.dto.AwaitingBattleResponseDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRespondRequest;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleResponse;
// import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleApplyUserRepository;
// import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
// import com.woowahanrabbits.battle_people.domain.user.domain.User;
// import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
// import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
// import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
// import com.woowahanrabbits.battle_people.domain.vote.dto.VoteOpinionDto;
// import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;
// import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
// import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;
// import com.woowahanrabbits.battle_people.config.AppProperties;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// public class BattleServiceImpl implements BattleService {
//
// 	private final BattleRepository battleRepository;
// 	private final VoteOpinionRepository voteOpinionRepository;
// 	private final BattleApplyUserRepository battleApplyUserRepository;
// 	private final VoteInfoRepository voteInfoRepository;
// 	private final UserRepository userRepository;
// 	private final AppProperties appProperties;
// 	private final UserVoteOpinionRepository userVoteOpinionRepository;
//
// 	@Override
// 	public void registBattle(BattleInviteRequest battleInviteRequest, User user) {
//
// 		Calendar calendar = Calendar.getInstance();
// 		calendar.setTime(battleInviteRequest.getStartDate());
// 		calendar.add(Calendar.MINUTE, battleInviteRequest.getTime());
// 		Date endDate = calendar.getTime();
//
// 		//VoteInfo 만들기
// 		VoteInfo voteInfo = VoteInfo.builder()
// 			.title(battleInviteRequest.getTitle())
// 			.startDate(battleInviteRequest.getStartDate())
// 			.endDate(endDate)
// 			.category(battleInviteRequest.getCategory())
// 			.currentState(0)
// 			.detail(battleInviteRequest.getDetail())
// 			.build();
//
// 		//투표 정보 저장
// 		voteInfoRepository.save(voteInfo);
//
// 		//VoteOpinion 만들기
// 		VoteOpinion voteOpinion = VoteOpinion.builder()
// 			.voteOpinionIndex(0)
// 			.voteInfoId(voteInfo.getId())
// 			.user(user)
// 			.opinion(battleInviteRequest.getOpinion())
// 			.build();
// 		voteOpinionRepository.save(voteOpinion);
//
// 		//battle board
// 		BattleBoard battleBoard = BattleBoard.builder()
// 			.registUser(user)
// 			.oppositeUser(userRepository.findById(battleInviteRequest.getOppositeUserId()).orElseThrow())
// 			.voteInfo(voteInfo)
// 			.maxPeopleCount(battleInviteRequest.getMaxPeopleCount())
// 			.build();
// 		battleRepository.save(battleBoard);
// 	}
//
// 	@Override
// 	public List<?> getRequestBattleList(String type, User user, int page) {
// 		Pageable pageable = PageRequest.of(page, 12);
// 		List<BattleBoard> list = null;
// 		if (type.equals("sent")) {
// 			list = battleRepository.findByRegistUserId(user.getId());
// 		} else if (type.equals("received")) {
// 			list = battleRepository.findByOppositeUserIdAndCurrentState(user.getId(), 0);
// 		}
//
// 		List<BattleBoard> newList = new PageImpl<>(list).getContent();
// 		// List<BattleResponse> returnList = new ArrayList<>();
// 		// for (BattleBoard battleBoard : newList) {
// 		// 	returnList.add(new BattleResponse(battleBoard, voteOpinionRepository.findByVoteInfoId(
// 		// 		battleBoard.getVoteInfo().getId())));
// 		// }
//
// 		return newList.stream().map(battleBoard -> new BattleResponse(
// 			battleBoard, voteOpinionRepository.findByVoteInfoId(battleBoard.getVoteInfo().getId())
// 		)).toList();
// 	}
//
// 	@Override
// 	public void acceptOrDeclineBattle(BattleRespondRequest battleRespondRequest, User user) {
// 		BattleBoard battleBoard = battleRepository.findById(battleRespondRequest.getBattleId()).orElseThrow();
//
// 		if (battleBoard.getOppositeUser().getId() != user.getId() || battleBoard.getCurrentState() != 0) {
// 			throw new RuntimeException();
// 		}
//
// 		//수락할 때
// 		if (battleRespondRequest.getRespond().equals("accept")) {
// 			battleRepository.updateBattleBoardStatus(battleRespondRequest.getBattleId(), 2, null);
// 			VoteOpinion voteOpinion = VoteOpinion.builder()
// 				.voteOpinionIndex(1)
// 				.voteInfoId(battleBoard.getVoteInfo().getId())
// 				.user(user)
// 				.opinion(battleRespondRequest.getContent())
// 				.build();
// 			voteOpinionRepository.save(voteOpinion);
// 		} else if (battleRespondRequest.getRespond().equals("decline")) {
// 			battleRepository.updateBattleBoardStatus(battleRespondRequest.getBattleId(), 1,
// 				battleRespondRequest.getContent());
// 		}
// 	}
//
// 	@Override
// 	public List<?> getAwaitingBattleList(Integer category, int page, User user) {
// 		Pageable pageable = PageRequest.of(page, 12);
// 		List<BattleBoard> tempList = battleRepository.findByCategoryAndCurrentState(category, 2);
// 		List<BattleBoard> list = new PageImpl<>(tempList, pageable, tempList.size()).toList();
// 		return list.stream()
// 			.map(battleBoard -> {
// 				List<VoteOpinionDto> voteOpinionDtos = voteOpinionRepository.findByVoteInfoId(
// 						battleBoard.getVoteInfo().getId())
// 					.stream()
// 					.map(VoteOpinionDto::new)
// 					.collect(Collectors.toList());
//
// 				int currentPeopleCount = battleApplyUserRepository.countByBattleBoardId(battleBoard.getId());
// 				boolean isVoted = battleApplyUserRepository.existsByBattleBoardIdAndUserId(battleBoard.getId(),
// 					user.getId());
//
// 				return AwaitingBattleResponseDto.builder()
// 					.id(battleBoard.getId())
// 					.title(battleBoard.getVoteInfo().getTitle())
// 					.opinionDtos(voteOpinionDtos)
// 					.startDate(battleBoard.getVoteInfo().getStartDate())
// 					.endDate(battleBoard.getVoteInfo().getEndDate())
// 					.maxPeopleCount(battleBoard.getMaxPeopleCount())
// 					.currentPeopleCount(currentPeopleCount)
// 					.isVoted(isVoted)
// 					.build();
// 			})
// 			.collect(Collectors.toList());
// 	}
//
// 	@Override
// 	public void applyBattle(BattleApplyDto battleApplyDto, User user) {
// 		BattleBoard battleBoard = battleRepository.findById(battleApplyDto.getBattleId()).orElseThrow();
// 		if (battleBoard.getOppositeUser().getId() == user.getId()
// 			|| battleBoard.getRegistUser().getId() == user.getId()) {
// 			//주최자는 참여 신청 X
// 			throw new RuntimeException();
// 		}
// 		if (battleBoard.getCurrentState() != 2) {
// 			//참여 모집중이 아닌 배틀
// 			throw new RuntimeException();
// 		}
// 		BattleApplyUser battleApplyUser = BattleApplyUser.builder()
// 			.battleBoard(battleBoard)
// 			.user(user)
// 			.selectedOpinion(battleApplyDto.getSelectedOpinion())
// 			.build();
// 		battleApplyUserRepository.save(battleApplyUser);
//
// 		int currentPeopleCount = battleApplyUserRepository.countByBattleBoardId(battleBoard.getId());
// 		if (currentPeopleCount > appProperties.getMinPeopleCount()) {
// 			battleRepository.updateBattleBoardStatus(battleBoard.getId(), 3, null);
// 		}
// 	}
// }
