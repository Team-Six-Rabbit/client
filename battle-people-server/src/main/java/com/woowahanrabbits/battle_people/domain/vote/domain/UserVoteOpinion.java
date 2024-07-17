package com.woowahanrabbits.battle_people.domain.vote.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@IdClass(UserVoteOpinionId.class)
public class UserVoteOpinion {
    @Id
    private Long userId;

    @Id
    private Long voteInfoId;

    private int voteInfoIndex;
}
