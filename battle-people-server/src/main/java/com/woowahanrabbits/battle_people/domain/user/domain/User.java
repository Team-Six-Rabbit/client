package com.woowahanrabbits.battle_people.domain.user.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

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
}
