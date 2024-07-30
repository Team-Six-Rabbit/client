package com.woowahanrabbits.battle_people.domain.user.dto;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import lombok.Getter;

@Getter
public class BasicUserDto {
	private final Long id;
	private final String nickname;
	private final String imgUrl;
	private final Integer rating;

	public BasicUserDto(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.imgUrl = user.getImgUrl();
		this.rating = user.getRating();
	}
}
