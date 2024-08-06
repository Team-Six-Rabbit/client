package com.woowahanrabbits.battle_people.domain.live.dto.response;

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
public class WriteTalkResponseDto {
	private Long userId;
	private String userNickname;
	@Setter
	private int userVote;
}
