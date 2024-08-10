package com.woowahanrabbits.battle_people.domain.vote.infrastructure;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

@Repository
public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

	List<VoteInfo> findByEndDateBeforeAndCurrentState(Date now, int currentState);

	List<VoteInfo> findByStartDateBeforeAndCurrentState(Date time, int currentState);

	Page<VoteInfo> findAllByCurrentStateOrderByStartDateDesc(int id, Pageable pageable);

	Page<VoteInfo> findAllByCurrentStateOrderByIdDesc(int status, Pageable pageable);

	Page<VoteInfo> findAllByCategoryAndCurrentStateOrderByIdDesc(Integer category, int status,
		Pageable pageable);

	Page<VoteInfo> findAllByCategoryAndCurrentStateOrderByStartDateDesc(Integer category, int currentState,
		Pageable pageable);
}

