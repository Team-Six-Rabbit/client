package com.woowahanrabbits.battle_people.domain.battle.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class VoteOpinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer voteOpinionIndex;
    private Long voteInfoId;
    private Long userId;
    private String Opinion;
    private Integer preCount;
    private Integer finalCount;


}
