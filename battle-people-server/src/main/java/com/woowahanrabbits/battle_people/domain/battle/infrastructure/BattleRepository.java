package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

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
}
