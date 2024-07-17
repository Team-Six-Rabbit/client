package com.woowahanrabbits.battle_people.domain.vote.domain;

import com.woowahanrabbits.battle_people.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(VoteOpinionId.class)
public class VoteOpinion {
    @Id
    private Integer voteOpinionIndex;

    @Id
    private Long voteInfoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String Opinion;
    private Integer preCount;
    private Integer finalCount;


}
