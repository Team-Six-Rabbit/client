package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface BattleRepositoryCustom {
	void updateBattleBoardStatus(Long battleId, String rejectionReason);

	Page<BattleBoard> findByUserIdAndType(Long userId, String type, Pageable pageable);

	List<Map<String, Object>> findAllByCategoryAndStatusAndConditions(int category, int status, Pageable pageable,
		User user);
}
