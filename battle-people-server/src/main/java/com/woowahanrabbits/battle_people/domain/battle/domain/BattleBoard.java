package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long registUserId;
	private Long oppositeUserId;
	private Long voteInfoId;
	private int minPeopleCount;
	private int maxPeopleCount;
	private String detail;
	private String battleRule;
	private Date registDate;
	private int currentState;
	private String rejectionReason;
	private String imageUrl;
	private String liveUri;
	private boolean isDeleted;

}
