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
import com.woowahanrabbits.battle_people.domain.user.dto.CreateLives;
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

	@Query("SELECT DISTINCT new com.woowahanrabbits.battle_people.domain.user.dto.CreateLives(b, "
		+ "(CASE "
		+ "WHEN v.currentState = 3 THEN 4 "  // 예정된 경우
		+ "WHEN SUM(CASE WHEN uvo.voteInfoIndex = 1 THEN 1 ELSE 0 END)"
		+ " = SUM(CASE WHEN uvo.voteInfoIndex = 2 THEN 1 ELSE 0 END) THEN 3 "
		// 무승부인 경우
		+ "WHEN SUM(CASE WHEN uvo.voteInfoIndex = 1 THEN 1 ELSE 0 END)"
		+ " > SUM(CASE WHEN uvo.voteInfoIndex = 2 THEN 1 ELSE 0 END) THEN 0 "
		// register 유저가 이긴 경우
		+ "ELSE 1 END)) "  // opposite 유저가 이긴 경우
		+ "FROM BattleBoard b "
		+ "JOIN b.voteInfo v "
		+ "LEFT JOIN UserVoteOpinion uvo ON uvo.voteInfo.id = v.id "
		+ "WHERE b.registUser.id = :userId "
		+ "AND (v.currentState = 3 OR v.currentState = 4 OR v.currentState = 8) "
		+ "GROUP BY b.id, v.currentState")
	List<CreateLives> getCreateLives(@Param("userId") Long userId);

	@Query("SELECT DISTINCT b FROM BattleBoard b"
		+ " LEFT JOIN BattleApplyUser bau ON b.id = bau.battleBoard.id"
		+ " WHERE b.registUser.id = :userId OR b.oppositeUser.id = :userId OR bau.user.id = :userId")
	List<BattleBoard> findMyAwaitingList(@Param("userId") Long userId);

}
