package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteOpinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @deprecated
 * This class is deprecated and will be removed in a future release.
 * Use {@link BattleResponse} instead.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Deprecated(forRemoval = true)
public class BattleReturnDto {

	// BattleBoard 관련 필드
	private BattleBoard battle;

	// VoteInfo 관련 필드
	private List<VoteOpinion> opinions;

}
