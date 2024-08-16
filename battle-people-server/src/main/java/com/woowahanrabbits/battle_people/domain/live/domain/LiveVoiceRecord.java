package com.woowahanrabbits.battle_people.domain.live.domain;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LiveVoiceRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "battle_board_id")
	private BattleBoard battleBoard;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int selectedOpinion;
	private String recordUrl;
	private String recordSummary;
}
