package com.woowahanrabbits.battle_people.domain.user.domain;

import java.time.LocalDate;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String img_url;

    @Column(nullable = false)
    private int rating;
    private String access_token;
    private LocalDate penalty_start_date;
    private LocalDate penalty_end_date;

}
