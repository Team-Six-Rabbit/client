package com.woowahanrabbits.battle_people.domain.balancegame.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.balancegame.service.BalanceGameService;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/balance-game")
@Tag(name = "BalanceGameController", description = "밸런스게임 컨트롤러")
public class BalanceGameController {

	private final BalanceGameService balanceGameService;

	public BalanceGameController(BalanceGameService balanceGameService) {
		this.balanceGameService = balanceGameService;
	}

	@PostMapping("")
	@Operation(summary = "[점화] 밸런스 게임을 생성한다.")
	public ResponseEntity<?> registBalanceGame(@RequestBody BattleReturnDto battleReturnDto) {
		balanceGameService.addBalanceGame(battleReturnDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("")
	@Operation(summary = "[점화] 카테고리 별, 진행 상태 별 밸런스 게임 조회 ")
	public ResponseEntity<?> getBalanceGameByConditions(@RequestParam int category,
		@RequestParam(defaultValue = "5") int status, @RequestParam int page,
		@RequestParam int userId) {
		User user = new User();
		user.setId(userId);

		return new ResponseEntity<>(balanceGameService.getBalanceGameByConditions(category, 5, 1, user), HttpStatus.OK);
		// return new ResponseEntity<>(balanceGameService.getBalanceGameByConditions(category, status), HttpStatus.OK);
	}

	@PatchMapping("")
	@Operation(summary = "밸런스 게임을 삭제합니다.")
	public ResponseEntity<?> deleteBalanceGame(@RequestParam Long id, @RequestParam int userId) {

		//JWT 토큰 전(리팩토링 필요)
		User user = new User();
		user.setId(userId);

		balanceGameService.deleteBalanceGame(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 대한 댓글을 불러옵니다.")
	public ResponseEntity<?> getCommentListByBattleId(@RequestParam Long id,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(balanceGameService.getCommentsByBattleId(id, pageable), HttpStatus.OK);
	}

	@PostMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 댓글을 작성합니다.")
	public ResponseEntity<?> addComment(@RequestBody BalanceGameCommentDto balanceGameCommentDto) {
		balanceGameService.addComment(balanceGameCommentDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// @PatchMapping("/comment")
	// @Operation(summary = "특정 밸런스 게임 내 댓글을 삭제합니다.")
	// public ResponseEntity<?> deleteComment(@Request ) {
	//
	// }
}
