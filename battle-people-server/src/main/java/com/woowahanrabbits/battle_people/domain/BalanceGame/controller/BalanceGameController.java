package com.woowahanrabbits.battle_people.domain.balancegame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.balancegame.service.BalanceGameService;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleReturnDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/balance-game")
@Tag(name = "BalanceGameController", description = "밸런스게임 컨트롤러")
public class BalanceGameController {

	@Autowired
	private BalanceGameService balanceGameService;

	@PostMapping("")
	@Operation(summary = "[점화] 밸런스 게임을 생성한다.")
	public ResponseEntity<?> registBalanceGame(@RequestBody BattleReturnDto battleReturnDto) {
		balanceGameService.addBalanceGame(battleReturnDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("")
	@Operation(summary = "[점화] 카테고리 별, 진행 상태 별 밸런스 게임 조회 ")
	public ResponseEntity<?> getBalanceGameByConditions(@RequestParam int category, @RequestParam int status,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(balanceGameService.getBalanceGameByConditions(category, status, pageable),
			HttpStatus.OK);
	}

	@DeleteMapping("")
	@Operation(summary = "밸런스 게임을 삭제합니다.")
	public ResponseEntity<?> deleteBalanceGame(@RequestParam Long id) {
		balanceGameService.deleteBalanceGame(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
