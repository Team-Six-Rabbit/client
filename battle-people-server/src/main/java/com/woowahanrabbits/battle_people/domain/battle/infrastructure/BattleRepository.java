package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

import jakarta.transaction.Transactional;

@Repository
public interface BattleRepository extends JpaRepository<BattleBoard, Long> {
	Page<BattleBoard> findByRegistUserIdAndCurrentState(Long regist_user_id, int currentState, Pageable pageable);

	Page<BattleBoard> findByOppositeUserIdAndCurrentState(long opposite_user_id, int currentState, Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE BattleBoard b SET b.currentState = 2 WHERE b.id = :battleId")
	void updateBattleBoardStatusTo2(Long battleId);

	@Modifying
	@Transactional
	@Query("UPDATE BattleBoard b SET b.currentState = 1, b.rejectionReason = :rejectionReason WHERE b.id = :battleId")
	void updateBattleBoardStatusAndRejectionReason(String rejectionReason, Long battleId);

	@Query("SELECT b FROM BattleBoard b WHERE b.currentState = 4 AND b.category = :category")
	List<BattleBoard> findAllLiveListByCurrentState(String category);
}
