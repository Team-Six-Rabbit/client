package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

@Repository
public interface BattleRepository {
	void save(BattleBoard battleBoard);
}
