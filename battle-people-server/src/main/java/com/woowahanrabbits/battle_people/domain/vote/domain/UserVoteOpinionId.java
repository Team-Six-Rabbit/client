package com.woowahanrabbits.battle_people.domain.vote.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserVoteOpinionId implements Serializable {
	private Long userId;
	private Long voteInfoId;

}
