// package com.woowahanrabbits.battle_people.domain.battle.controller;
//
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.web.PageableDefault;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.VoteAcceptDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.VoteDeclineDto;
// import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
// import com.woowahanrabbits.battle_people.domain.user.domain.User;
// import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
// import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;
//
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
//
// @RestController
// @RequestMapping("/battle")
// @Tag(name = "BattleController", description = "배틀 컨트롤러")
// public class BattleController {
//
// 	private final BattleService battleService;
// 	private final VoteService voteService;
//
// 	public BattleController(BattleService battleService, VoteService voteService) {
// 		this.battleService = battleService;
// 		this.voteService = voteService;
// 	}
//
// 	//배틀 등록
// 	@PostMapping("/invite")
// 	@Operation(summary = "[점화] 배틀을 요청한다.")
// 	public ResponseEntity<?> registBattle(@RequestBody BattleBoard battleBoard, @RequestParam String opinion) {
// 		// battleService.registBattle(batttleRegistDto);
// 		System.out.println(battleBoard.toString());
// 		System.out.println(opinion);
// 		//투표 정보 저장
// 		voteService.addVoteInfo(battleBoard.getVoteInfo());
//
// 		//투표 의견 저장
// 		VoteOpinion voteOpinion = new VoteOpinion();
// 		voteOpinion.setOpinion(opinion);
// 		voteOpinion.setUser(battleBoard.getRegistUser());
// 		voteOpinion.setVoteInfoId(battleBoard.getVoteInfo().getId());
// 		voteOpinion.setVoteOpinionIndex(0);
// 		voteService.addVoteOpinion(voteOpinion);
//
// 		//Battle 저장
// 		battleService.addBattle(battleBoard);
// 		return new ResponseEntity<>(HttpStatus.OK);
// 	}
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
// }

