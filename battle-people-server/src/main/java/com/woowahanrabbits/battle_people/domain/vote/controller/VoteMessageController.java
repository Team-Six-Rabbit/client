package com.woowahanrabbits.battle_people.domain.vote.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.woowahanrabbits.battle_people.domain.vote.dto.CurrentVoteResponseDto;
import com.woowahanrabbits.battle_people.domain.vote.dto.VoteRequest;
import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VoteMessageController {

	private final VoteService voteService;

	@MessageMapping("/live-vote/{battleBoardId}")
	@SendTo("/topic/live-voteResults/{battleBoardId}")
	public CurrentVoteResponseDto handleVote(VoteRequest voteRequest) {
		return voteService.putVoteOpinion(voteRequest.getUserId(), voteRequest.getBattleBoardId(),
			voteRequest.getVoteInfoIndex());
	}
}
