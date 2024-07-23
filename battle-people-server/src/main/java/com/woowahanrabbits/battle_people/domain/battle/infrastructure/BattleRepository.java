package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

import jakarta.transaction.Transactional;

@Repository
public interface BattleRepository extends JpaRepository<BattleBoard, Long>, BattleRepositoryCustom {
	BattleBoard findByVoteInfoId(Long voteInfoId);
	List<BattleBoard> findByVoteInfo_CategoryAndCurrentState(int category, int currentState);
}
