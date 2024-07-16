package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.VoteOpinion;

@Repository
public interface VoteOpinionRepository extends JpaRepository<VoteOpinion, Long> {
	List<VoteOpinion> findByVoteInfoId(Long voteInfoId);
}
