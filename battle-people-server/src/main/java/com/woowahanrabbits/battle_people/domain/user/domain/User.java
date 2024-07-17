package com.woowahanrabbits.battle_people.domain.user.domain;

import java.time.LocalDate;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;
>>>>>>> 241f410ded449e887607455827aaacbeb8710976
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

<<<<<<< HEAD
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String email;
	private String password;
	private String nickname;
	private String img_url;
	private int rating;
	private String access_token;
	private LocalDate penalty_start_date;
	private LocalDate penalty_end_date;
=======
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
>>>>>>> 241f410ded449e887607455827aaacbeb8710976
}
