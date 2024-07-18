package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.live.domain.Room;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
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
    @JoinColumn(name = "regist_user_id", nullable = false)
    private User registUser;

    @ManyToOne
    @JoinColumn(name = "opposite_user_id")
    private User oppositeUser;

    @OneToOne
    @JoinColumn(name = "vote_info_id")
    private VoteInfo voteInfo;

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

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PrePersist
    protected void onCreate() {
        this.registDate = new Date();
    }


}
