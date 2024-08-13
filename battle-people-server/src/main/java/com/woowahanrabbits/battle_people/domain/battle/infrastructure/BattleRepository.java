package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

@Repository
public interface BattleRepository extends JpaRepository<BattleBoard, Long> {

	Page<BattleBoard> findByOppositeUserIdAndVoteInfoCurrentState(long id, int currentState, Pageable pageable);

	@Query("SELECT v FROM BattleBoard b JOIN b.voteInfo v "
		+ "WHERE b.registUser.id = :registUserId OR b.oppositeUser.id = :oppositeUserId")
	List<VoteInfo> findVoteInfosByUserIds(@Param("registUserId") long registUserId,
		@Param("oppositeUserId") long oppositeUserId);

	BattleBoard findByVoteInfoId(Long id);

	@Query("SELECT COUNT(v) > 0 FROM BattleBoard b JOIN b.voteInfo v "
		+ "WHERE (b.registUser.id = :userId OR b.oppositeUser.id = :userId) AND"
		+ "(v.currentState = 2 or v.currentState = 3 or v.currentState = 4 ) AND "
		+ "((v.startDate BETWEEN :startDate AND :endDate) OR "
		+ "(v.endDate BETWEEN :startDate AND :endDate))")
	boolean checkMyBattles(@Param("userId") long userId,
		@Param("startDate") Date startDate,
		@Param("endDate") Date endDate);

	@Query("SELECT b FROM BattleBoard b JOIN b.voteInfo v "
		+ "WHERE (b.registUser.id = :userId OR b.oppositeUser.id = :userId) AND"
		+ "(v.currentState = 3 or v.currentState = 4 or v.currentState = 8)")
	List<BattleBoard> getCreateLives(Long userId);
}
