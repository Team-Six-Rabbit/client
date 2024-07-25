package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

@Repository
public interface BattleRepository extends JpaRepository<BattleBoard, Long>, BattleRepositoryCustom {
	BattleBoard findByVoteInfoId(Long voteInfoId);

	Page<BattleBoard> findByVoteInfo_CategoryAndCurrentState(int category, int currentState, Pageable pageable);
}
