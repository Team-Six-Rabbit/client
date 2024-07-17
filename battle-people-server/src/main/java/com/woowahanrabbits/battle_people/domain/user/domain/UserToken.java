package com.woowahanrabbits.battle_people.domain.user.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String accessToken;
    private String refreshToken;
}
