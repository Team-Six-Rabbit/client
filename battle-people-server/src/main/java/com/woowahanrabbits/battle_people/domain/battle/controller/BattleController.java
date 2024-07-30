package com.woowahanrabbits.battle_people.domain.battle.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api_response.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.VoteAcceptDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.VoteDeclineDto;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/battle")
@Tag(name = "BattleController", description = "배틀 컨트롤러")
public class BattleController {

	private final BattleService battleService;
	private final VoteService voteService;

	public BattleController(BattleService battleService, VoteService voteService) {
		this.battleService = battleService;
		this.voteService = voteService;
	}

	//배틀 등록
	@PostMapping("/invite")
	@Operation(summary = "[점화] 배틀을 요청한다.")
	public ResponseEntity<?> registBattle(@RequestBody BattleBoard battleBoard, @RequestParam String opinion) {

		try {
			//투표 정보 저장
			voteService.addVoteInfo(battleBoard.getVoteInfo());

			//투표 의견 저장
			VoteOpinion voteOpinion = new VoteOpinion();
			voteOpinion.setOpinion(opinion);
			voteOpinion.setUser(battleBoard.getRegistUser());
			voteOpinion.setVoteInfoId(battleBoard.getVoteInfo().getId());
			voteOpinion.setVoteOpinionIndex(0);
			voteService.addVoteOpinion(voteOpinion);

			//Battle 저장
			battleService.addBattle(battleBoard);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	//요청한, 요청받은 배틀 조회
	@GetMapping("")
	@Operation(summary = "요청한, 요청받는 배틀을 조회한다.")
	public ResponseEntity<?> getRequestBattleList(@RequestParam String type, @RequestParam Long userId, int page) {
		try {
			Page<?> list = battleService.getBattleList(type, userId, page);
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", map));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	//
	@PatchMapping("/accept")
	@Operation(summary = "[불씨] 배틀을 수락한다.")
	public ResponseEntity<?> acceptBattle(@RequestBody VoteAcceptDto voteAcceptDto) {

		try {
			//BattleBoard 내 current_state update해주기
			Long battleId = battleService.getBattleBoardByVoteInfoId(voteAcceptDto.getVoteInfoId()).getId();
			battleService.updateBattleStatus(battleId, null);

			//userId로 User 가져오기
			User user = new User();
			user.setId(voteAcceptDto.getUserId());

			//voteOpinion에 상대 의견 추가하기
			VoteOpinion voteOpinion = new VoteOpinion();
			voteOpinion.setVoteOpinionIndex(1);
			voteOpinion.setOpinion(voteAcceptDto.getOpinion());
			voteOpinion.setUser(user);
			voteOpinion.setVoteInfoId(voteAcceptDto.getVoteInfoId());
			voteService.addVoteOpinion(voteOpinion);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	@PatchMapping("/decline")
	@Operation(summary = "[불발/연기] 요청받은 배틀을 거절한다.")
	public ResponseEntity<?> declineBattle(@RequestBody VoteDeclineDto voteDeclineDto) {

		try {
			battleService.updateBattleStatus(voteDeclineDto.getBattleId(), voteDeclineDto.getRejectionReason());
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	@GetMapping("/apply-list")
	@Operation(summary = "[불씨] 모집중인 배틀을 조회한다.")
	public ResponseEntity<?> getAwaitingBattleList(@RequestParam int category, int page) {
		try {
			Page<?> list = battleService.getAwaitingBattleList(category, page);
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", map));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/apply-user-list/{battleBoardId}")
	@Operation(summary = "[불씨] 모집중인 특정 배틀에 참여 신청한 유저를 조회한다.")
	public ResponseEntity<?> getApplyUserList(@PathVariable Long battleBoardId, int page) {
		try {
			Page<?> list = battleService.getApplyUserList(battleBoardId, page);
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", map));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	@PostMapping("/apply")
	@Operation(summary = "모집중인 특정 배틀에 참여 신청한다.")
	public ResponseEntity<?> applyBattle(@RequestBody BattleApplyDto battleApplyDto) {
		try {
			battleService.addBattleApplyUser(battleApplyDto);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}
}
