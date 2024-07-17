package com.woowahanrabbits.battle_people.domain.vote.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVoteOpinionId implements Serializable {
    private Long userId;
    private Long voteInfoId;

}
