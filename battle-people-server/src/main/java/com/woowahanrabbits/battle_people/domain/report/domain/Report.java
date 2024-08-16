package com.woowahanrabbits.battle_people.domain.report.domain;

import com.woowahanrabbits.battle_people.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "regist_user_id")
    private User registUser;

    private int tableCode;
    private Long sequenceId;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    private int reportCode;
    private String context;
    private Date registDate;
    private boolean isComplete;

    @PrePersist
    protected void onCreate() {
        this.registDate = new Date();
    }
}
