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
import com.woowahanrabbits.battle_people.domain.battle.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;

@RestController
@RequestMapping("/battle")
public class BattleController {

	private final BattleService battleService;

	public BattleController(BattleService battleService) {
		this.battleService = battleService;
	}

	@PostMapping("/invite")
	public ResponseEntity<?> registBattle(@RequestBody BattleRegistDto batttleRegistDto) {
		battleService.registBattle(batttleRegistDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<?> getBattle(@RequestParam String type, @RequestParam Long user_id ) {
		battleService.getBattleList(type, user_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/accept")
	public ResponseEntity<?> acceptBattle(@RequestBody VoteOpinion voteOpinion, @RequestParam Long battle_id ) {
		battleService.acceptBattle(voteOpinion, battle_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/decline")
	public ResponseEntity<?> declineBattle(@RequestParam Long battle_id, @RequestParam String rejection_reason ) {
		battleService.declineBattle(rejection_reason, battle_id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
