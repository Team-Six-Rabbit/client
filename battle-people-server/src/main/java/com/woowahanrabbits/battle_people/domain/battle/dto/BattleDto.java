package com.woowahanrabbits.battle_people.domain.battle.dto;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

import lombok.Getter;

@Getter
public class BattleDto {
	private final Long id;
	private final BasicUserDto registUser;
	private final BasicUserDto oppositeUser;
	private final VoteInfo voteInfo;
	private final int minPeopleCount;
	private final int maxPeopleCount;
	private final String detail;
	private final String battleRule;
	private final Date registDate;
	private final int currentState;
	private final String rejectionReason;
	private final String imageUrl;
	private final String liveUri;

	public BattleDto(BattleBoard battleBoard) {
		this.id = battleBoard.getId();
		this.registUser = new BasicUserDto(battleBoard.getRegistUser());
		this.oppositeUser = new BasicUserDto(battleBoard.getOppositeUser());
		this.voteInfo = battleBoard.getVoteInfo();
		this.minPeopleCount = battleBoard.getMinPeopleCount();
		this.maxPeopleCount = battleBoard.getMaxPeopleCount();
		this.detail = battleBoard.getDetail();
		this.battleRule = battleBoard.getBattleRule();
		this.registDate = battleBoard.getRegistDate();
		this.currentState = battleBoard.getCurrentState();
		this.rejectionReason = battleBoard.getRejectionReason();
		this.imageUrl = battleBoard.getImageUrl();
		this.liveUri = battleBoard.getLiveUri();
	}
}
