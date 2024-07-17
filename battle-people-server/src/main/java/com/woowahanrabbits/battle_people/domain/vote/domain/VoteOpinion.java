package com.woowahanrabbits.battle_people.domain.vote.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(VoteOpinionId.class)
public class VoteOpinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer voteOpinionIndex;

    @Id
    private Long voteInfoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;
    private String Opinion;
    private Integer preCount;
    private Integer finalCount;


}
