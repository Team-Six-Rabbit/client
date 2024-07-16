package com.woowahanrabbits.battle_people.domain.user.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCustom {
	private String email;
	private String password;
	private String nickname;
	private String img_url;
	private int rating;
	private String access_token;
	private LocalDate penalty_start_date;
	private LocalDate penalty_end_date;
}
