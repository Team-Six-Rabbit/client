package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUser;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleApplyUserId;

@Repository
public interface BattleApplyUserRepository
	extends JpaRepository<BattleApplyUser, BattleApplyUserId> { // Composite key 적용

	Page<BattleApplyUser> findByBattleBoard_Id(Long battleBoardId, Pageable pageable); // 정확한 필드 이름 사용
}

