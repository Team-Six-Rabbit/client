package com.woowahanrabbits.battle_people.domain.battle.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRegistDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.VoteAcceptDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.VoteDeclineDto;
import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

@RestController
@RequestMapping("/battle")
public class BattleController {

	private final BattleService battleService;
	private final VoteService voteService;

	public BattleController(BattleService battleService, VoteService voteService) {
		this.battleService = battleService;
		this.voteService = voteService;
	}

	@PostMapping("/invite")
	public ResponseEntity<?> registBattle(@RequestBody BattleBoard battleBoard, @RequestParam String opinion) {
		// battleService.registBattle(batttleRegistDto);
		System.out.println(battleBoard.toString());
		System.out.println(opinion);
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
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<?> getRequestBattleList(@RequestParam String type, @RequestParam Long user_id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		return new ResponseEntity<>(battleService.getBattleList(type, user_id, pageable), HttpStatus.OK);
	}

	@PostMapping("/accept")
	public ResponseEntity<?> acceptBattle(@RequestBody VoteAcceptDto voteAcceptDto) {
		//BattleBoard 내 current_state update해주기
		Long battle_id = battleService.getBattleBoardByVoteInfoId(voteAcceptDto.getVoteInfoId()).getId();
		battleService.updateBattleStatus(battle_id, null);

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
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/decline")
	public ResponseEntity<?> declineBattle(@RequestBody VoteDeclineDto voteDeclineDto) {
		battleService.updateBattleStatus(voteDeclineDto.getBattleId(), voteDeclineDto.getRejectionReason());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// @GetMapping("/apply-list")
	// public ResponseEntity<?> getBattleList(@RequestParam String category) {
	// 	return new ResponseEntity<>(battleService.getBattleList(category), HttpStatus.OK);
	// }


}
