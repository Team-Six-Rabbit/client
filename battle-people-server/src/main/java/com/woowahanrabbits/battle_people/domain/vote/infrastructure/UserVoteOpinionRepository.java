package com.woowahanrabbits.battle_people.domain.vote.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinionId;
import com.woowahanrabbits.battle_people.domain.vote.domain.VoteInfo;

@Repository
public interface UserVoteOpinionRepository  extends JpaRepository<UserVoteOpinion, UserVoteOpinionId> {

	@Query(value = "SELECT vi.id AS battleId, vi.title AS title, vo.opinion AS opinion,"
		+ "(SELECT COUNT(*) FROM user_vote_opinion uvo WHERE uvo.vote_info_id = vo.vote_info_id AND uvo.vote_info_index = vo.vote_opinion_index) AS voteCount, "
		+ "vi.start_date, vi.end_date "
		+ "FROM vote_info vi "
		+ "JOIN vote_opinion vo ON vi.id = vo.vote_info_id "
		+ "ORDER BY vi.end_date DESC, vi.id, vo.vote_opinion_index", nativeQuery = true)
	List<Object[]> findAllVotesWithCountsAndUserVoteStatus(@Param("userId") Long userId);
}
