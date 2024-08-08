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
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriteTalkResponseDto {
	private BasicUserDto basicUserDto;
	private int userVote;
	private int idx;
}
