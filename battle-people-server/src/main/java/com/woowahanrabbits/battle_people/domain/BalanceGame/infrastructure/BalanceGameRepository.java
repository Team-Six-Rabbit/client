package com.woowahanrabbits.battle_people.domain.balancegame.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woowahanrabbits.battle_people.domain.balancegame.domain.BalanceGameBoardComment;

public interface BalanceGameRepository extends JpaRepository<BalanceGameBoardComment, Long> {

	@Query(
		"SELECT new com.woowahanrabbits.battle_people.domain.balancegame.dto."
			+ "BalanceGameCommentDto(b.id, b.battleBoard.id, b.user, b.content, b.registDate) "
			+ "FROM BalanceGameBoardComment b "
			+ "WHERE b.battleBoard.id = :battleBoardId")
	List<BalanceGameCommentDto> findCommentsByBattleBoardId(@Param("battleBoardId") Long battleBoardId);
}
