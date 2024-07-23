package com.woowahanrabbits.battle_people.domain.vote.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

@Repository
public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {

	@Query(value="SELECT vi.id AS battleId, vi.title AS title, vo.opinion AS opinion, "
		+ "(SELECT COUNT(*) FROM user_vote_opinion uvo WHERE uvo.vote_info_id = vo.vote_info_id AND uvo.vote_info_index = vo.vote_opinion_index) AS voteCount, "
		+ "       vi.start_date, vi.end_date, vi.category, bb.current_state "
		+ "FROM vote_info vi "
		+ "JOIN vote_opinion vo ON vi.id = vo.vote_info_id "
		+ "JOIN battle_board bb on bb.vote_info_id = vi.id "
		+ "WHERE bb.current_state = :status "
		+ "ORDER BY vi.end_date DESC, vi.id, vo.vote_opinion_index"
		, nativeQuery = true)
	List<Object[]> findAllByStatus(@Param("status") int status);

	@Query(value="SELECT vi.id AS battleId, vi.title AS title, vo.opinion AS opinion, "
		+ "(SELECT COUNT(*) FROM user_vote_opinion uvo WHERE uvo.vote_info_id = vo.vote_info_id AND uvo.vote_info_index = vo.vote_opinion_index) AS voteCount, "
		+ "       vi.start_date, vi.end_date, vi.category, bb.current_state "
		+ "FROM vote_info vi "
		+ "JOIN vote_opinion vo ON vi.id = vo.vote_info_id "
		+ "JOIN battle_board bb on bb.vote_info_id = vi.id "
		+ "WHERE bb.current_state = :status "
		+ "AND vi.category = :category "
		+ "ORDER BY vi.end_date DESC, vi.id, vo.vote_opinion_index"
		, nativeQuery = true)
	List<Object[]> findAllByCategoryAndStatus(@Param("category") Integer category, @Param("status") int status);

}

