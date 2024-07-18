package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

public interface BattleRepositoryCustom {
	void updateBattleBoardStatus(Long battleId, String rejectionReason);
}
