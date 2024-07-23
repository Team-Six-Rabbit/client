package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

public interface BattleRepositoryCustom {
	void updateBattleBoardStatus(Long battleId, String rejectionReason);
	List<BattleBoard> findByUserIdAndType(Long userId, String type);

}
