package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class BattleBoard {
	static long id;
	static long registUserId;
	static long oppositeUserId;
	static long voteInfoId;
	static int minPeopleCount;
	static int maxPeopleCount;
	static String detail;
	static String battleRule;
	static Date registDate;
	static int currentState;
	static String rejectionReason;
	static String imageUrl;
	static String liveUri;
	static boolean isDeleted;

}
