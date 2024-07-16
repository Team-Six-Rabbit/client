package com.woowahanrabbits.battle_people.domain.battle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;

@RestController
public class BattleController {

	private final BattleService battleService;

	public BattleController(BattleService battleService) {
		this.battleService = battleService;
	}

	@PostMapping("/battle/invite")
	public ResponseEntity<?> registBattle(@RequestBody BattleRegistDto batttleRegistDto) {
		battleService.registBattle(batttleRegistDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// @GetMapping("/battle")
	// public ResponseEntity<?> getBattle(@RequestParam String type ) {
	// 	int user_id = 1;
	// 	battleService.getBattleList(type, user_id);
	// }
}
