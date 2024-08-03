package com.woowahanrabbits.battle_people.domain.vote.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;
import com.woowahanrabbits.battle_people.domain.vote.dto.GetVoteInfoWithUserCountDto;

@Repository
public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

	@Procedure(procedureName = "GetVoteInfoWithUserCount")
	Page<GetVoteInfoWithUserCountDto> getVoteInfoWithUserCount(@Param("userId") Long userId, Pageable pageable);

	Page<VoteInfo> findByCurrentStateAndCategory(int status, Integer category, Pageable pageable);

	Page<VoteInfo> findByCurrentState(int status, Pageable pageable);

	Page<VoteInfo> findAllByCurrentState(int currentState, Pageable pageable);

	Page<VoteInfo> findAllByCategoryAndCurrentState(Integer category, int currentState, Pageable pageable);
}

