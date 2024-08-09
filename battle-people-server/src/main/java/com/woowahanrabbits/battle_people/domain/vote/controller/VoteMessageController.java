package com.woowahanrabbits.battle_people.domain.vote.controller;

import org.springframework.stereotype.Controller;

import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VoteMessageController {

	private final VoteService voteService;

	// @MessageMapping("/live-vote/{battleBoardId}")
	// @SendTo("/topic/live-voteResults/{battleBoardId}")
	// public CurrentVoteResponseDto handleVote(VoteRequest voteRequest) {
	//
	// 	System.out.println("vote");
	// 	return voteService.putVoteOpinion(voteRequest.getUserId(), voteRequest.getBattleBoardId(),
	// 		voteRequest.getVoteInfoIndex());
	// }
	//
	// @GetMapping("/addTopicListener")
	// public void addTopicListener(@RequestParam Long battleBoardId) {
	// 	voteService.addTopicListener(battleBoardId);
	// }

}
