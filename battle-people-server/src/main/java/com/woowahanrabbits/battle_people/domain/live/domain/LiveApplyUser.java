package com.woowahanrabbits.battle_people.domain.live.domain;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class LiveApplyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "battle_board_id")
    private BattleBoard battleBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date inTime;
    private Date outTime;

    @PrePersist
    protected void onCreate() {
        this.inTime = new Date();
    }
}
