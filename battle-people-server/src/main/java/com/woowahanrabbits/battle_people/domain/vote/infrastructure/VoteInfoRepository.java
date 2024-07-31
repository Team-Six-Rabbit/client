package com.woowahanrabbits.battle_people.domain.vote.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

@Repository
public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

	@Query(value = "select bb.id as battleId, vi.id as voteInfoId, vi.title as title, vi.start_date as startDate, "
		+ "vi.end_date as endDate, vi.category as category, bb.current_state as currentState, bb.detail as detail "
		+ "from vote_info vi left join battle_board bb on bb.vote_info_id = vi.id "
		+ "where bb.current_state = :status "
		+ "order by vi.end_date desc, vi.id desc",
		nativeQuery = true)
	List<Object[]> findAllByStatus(@Param("status") int status);

	@Query(value = "select bb.id as battleId, vi.id as voteInfoId, vi.title as title, vi.start_date as startDate, "
		+ "vi.end_date as endDate, vi.category as category, bb.current_state as currentState, bb.detail as detail "
		+ "from vote_info vi left join battle_board bb on bb.vote_info_id = vi.id "
		+ "WHERE bb.current_state = :status "
		+ "AND vi.category = :category "
		+ "order by vi.end_date desc, vi.id desc",
		nativeQuery = true)
	List<Object[]> findAllByCategoryAndStatus(@Param("category") Integer category,
		@Param("status") int status);

	@Query(value = "select bb.id as battleId, vi.id as voteInfoId, vi.title as title, vi.start_date as startDate, "
		+ "vi.end_date as endDate, vi.category as category, bb.current_state as currentState, bb.detail as detail "
		+ "from vote_info vi left join battle_board bb on vi.id = bb.vote_info_id "
		+ "where bb.id = :id",
		nativeQuery = true)
	List<Object[]> findByBattleId(@Param("id") Long id);

}

