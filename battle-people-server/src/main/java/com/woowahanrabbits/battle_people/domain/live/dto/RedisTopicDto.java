package com.woowahanrabbits.battle_people.domain.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisTopicDto<T> {
	private String type;
	private Long battleBoardId;
	private T responseDto;
}
