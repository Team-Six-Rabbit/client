package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

public interface BattleRepositoryCustom {
	void updateBattleBoardStatus(Long battleId, String rejectionReason);
	Page<BattleBoard> findByUserIdAndType(Long userId, String type, Pageable pageable);

}
