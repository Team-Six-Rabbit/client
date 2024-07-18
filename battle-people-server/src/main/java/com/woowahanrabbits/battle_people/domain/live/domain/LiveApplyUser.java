package com.woowahanrabbits.battle_people.domain.live.domain;

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
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant;

    @Column(nullable = false)
    private Date inTime;
    private Date outTime;

    @PrePersist
    protected void onCreate() {
        this.inTime = new Date();
    }
}
