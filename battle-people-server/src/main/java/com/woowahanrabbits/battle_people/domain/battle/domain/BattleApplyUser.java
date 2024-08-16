package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
@IdClass(BattleApplyUserId.class)
public class BattleApplyUser {

	@Id
	@ManyToOne
	@JoinColumn(name = "battle_board_id")
	private BattleBoard battleBoard;

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int selectedOpinion;
	private Date applyDate;

	@PrePersist
	protected void onCreate() {
		this.applyDate = new Date();
	}

}
