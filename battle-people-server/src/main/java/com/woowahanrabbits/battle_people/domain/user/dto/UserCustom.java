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
	private String imgUrl;
	private int rating;
	private String accessToken;
	private LocalDate penaltyStartDate;
	private LocalDate penaltyEndDate;
}
