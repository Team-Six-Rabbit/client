package com.woowahanrabbits.battle_people.domain.battle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/battle")
@Tag(name = "BattleController", description = "배틀 컨트롤러")
public class BattleController {

	private final BattleService battleService;
	private final VoteService voteService;
	private final UserRepository userRepository;
	private final VoteInfoRepository voteInfoRepository;

	public BattleController(BattleService battleService, VoteService voteService, UserRepository userRepository,
		VoteInfoRepository voteInfoRepository) {
		this.battleService = battleService;
		this.voteService = voteService;
		this.userRepository = userRepository;
		this.voteInfoRepository = voteInfoRepository;
	}

	//배틀 등록
	@PostMapping("/invite")
	@Operation(summary = "[점화] 배틀을 요청한다.")
	public ResponseEntity<?> registBattle(@RequestBody BattleInviteRequest battleInviteRequest,
		@RequestParam int userId) {

		try {
			User user = userRepository.findById((long)userId).orElseThrow();

			battleService.registBattle(battleInviteRequest, user);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("error", "", null));
		}
	}
	//
	// 	//요청한, 요청받은 배틀 조회
	// 	@GetMapping("")
	// 	@Operation(summary = "요청한, 요청받는 배틀을 조회한다.")
	// 	public ResponseEntity<?> getRequestBattleList(@RequestParam String type, @RequestParam Long userId,
	// 		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
	// 		return new ResponseEntity<>(battleService.getBattleList(type, userId, pageable), HttpStatus.OK);
	// 	}
	//
	// 	//
	// 	@PostMapping("/accept")
	// 	@Operation(summary = "[불씨] 배틀을 수락한다.")
	// 	public ResponseEntity<?> acceptBattle(@RequestBody VoteAcceptDto voteAcceptDto) {
	// 		//BattleBoard 내 current_state update해주기
	// 		Long battleId = battleService.getBattleBoardByVoteInfoId(voteAcceptDto.getVoteInfoId()).getId();
	// 		battleService.updateBattleStatus(battleId, null);
	//
	// 		//userId로 User 가져오기
	// 		User user = new User();
	// 		user.setId(voteAcceptDto.getUserId());
	//
	// 		//voteOpinion에 상대 의견 추가하기
	// 		VoteOpinion voteOpinion = new VoteOpinion();
	// 		voteOpinion.setVoteOpinionIndex(1);
	// 		voteOpinion.setOpinion(voteAcceptDto.getOpinion());
	// 		voteOpinion.setUser(user);
	// 		voteOpinion.setVoteInfoId(voteAcceptDto.getVoteInfoId());
	// 		voteService.addVoteOpinion(voteOpinion);
	// 		return new ResponseEntity<>(HttpStatus.OK);
	// 	}
	//
	// 	@PostMapping("/decline")
	// 	@Operation(summary = "[불발/연기] 요청받은 배틀을 거절한다.")
	// 	public ResponseEntity<?> declineBattle(@RequestBody VoteDeclineDto voteDeclineDto) {
	// 		battleService.updateBattleStatus(voteDeclineDto.getBattleId(), voteDeclineDto.getRejectionReason());
	// 		return new ResponseEntity<>(HttpStatus.OK);
	// 	}
	//
	// 	@GetMapping("/apply-list")
	// 	@Operation(summary = "[불씨] 모집중인 배틀을 조회한다.")
	// 	public ResponseEntity<?> getAwaitingBattleList(@RequestParam int category,
	// 		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
	// 		return new ResponseEntity<>(battleService.getAwaitingBattleList(category, pageable), HttpStatus.OK);
	// 	}
	//
	// 	@GetMapping("/apply-user-list/{battleBoardId}")
	// 	@Operation(summary = "[불씨] 모집중인 특정 배틀에 참여 신청한 유저를 조회한다.")
	// 	public ResponseEntity<?> getApplyUserList(@PathVariable Long battleBoardId,
	// 		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
	// 		return new ResponseEntity<>(battleService.getApplyUserList(battleBoardId, pageable), HttpStatus.OK);
	// 	}
	//
	// 	@PostMapping("/apply")
	// 	@Operation(summary = "모집중인 특정 배틀에 참여 신청한다.")
	// 	public ResponseEntity<?> applyBattle(@RequestBody BattleApplyDto battleApplyDto) {
	// 		battleService.addBattleApplyUser(battleApplyDto);
	// 		return new ResponseEntity<>(HttpStatus.OK);
	// 	}
}

