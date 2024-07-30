package com.woowahanrabbits.battle_people.domain.live.domain;

import java.util.Date;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class LiveApplyUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@ManyToOne
	@JoinColumn(name = "participant_id", nullable = false)
	private User participant;

	@Column(nullable = false)
	private Date inTime;
	private Date outTime;

	private String role;

	@PrePersist
	protected void onCreate() {
		this.inTime = new Date();
	}
}
