package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "regist_user_id")
    private Long registUserId;

    @ManyToOne
    @JoinColumn(name = "opposite_user_id")
    private Long oppositeUserId;

    @OneToOne
    @JoinColumn(name = "vote_info_id")
    private Long voteInfoId;

    private int minPeopleCount;
    private int maxPeopleCount;
    private String detail;
    private String battleRule;
    private Date registDate;
    private int currentState;
    private String rejectionReason;
    private String imageUrl;
    private String liveUri;
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        this.registDate = new Date();
    }
}
