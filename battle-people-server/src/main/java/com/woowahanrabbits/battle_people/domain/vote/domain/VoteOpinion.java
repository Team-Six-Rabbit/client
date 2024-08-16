package com.woowahanrabbits.battle_people.domain.vote.domain;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@IdClass(VoteOpinionId.class)
public class VoteOpinion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer voteOpinionIndex;

	@Id
	private Long voteInfoId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String opinion;
	private Integer preCount;
	private Integer finalCount;

}
