package com.woowahanrabbits.battle_people.domain.vote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {
	private final VoteService voteService;

	@PostMapping("/user-vote/{battleId}")
	@Operation(summary = "[]")
	public ResponseEntity<ApiResponseDto<CurrentVoteResponseDto>> putUserVote(@PathVariable Long battleId,
		@RequestParam Long userId,
		@RequestParam Integer voteOpinionIndex) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "",
					voteService.putVoteOpinion(userId, battleId, voteOpinionIndex)));

		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "", null));
		}
	}

	@GetMapping("/user-vote/{battleId}")
	@Operation(summary = "[]")
	public ResponseEntity<ApiResponseDto<CurrentVoteResponseDto>> getUserVote(@PathVariable Long battleId) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "",
					voteService.getVoteResult(battleId)));
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "", null));
		}
	}
}
