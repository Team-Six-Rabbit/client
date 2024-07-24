package com.woowahanrabbits.battle_people.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinRequest {
	private String email;
	private String password;
	private String nickname;
}
