package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

@Repository
public interface BattleRepository extends JpaRepository<BattleBoard, Long> {
	List<BattleBoard> findByRegistUserIdAndCurrentState(Long regist_user_id, int currentState);

	List<BattleBoard> findByOppositeUserIdAndCurrentState(long opposite_user_id, int currentState);
}
