package com.woowahanrabbits.battle_people.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String email;
	private String nickname;
	private int rating;
	private String imgUrl;
}
