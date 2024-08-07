package com.woowahanrabbits.battle_people.domain.live.dto.response;

import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriteChatResponseDto {
	private BasicUserDto user;
	private String message;
	@Setter
	private Integer userVote;

	@Setter
	private int idx;

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
	// private Date regDate;

}
