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

	@Query(value = "SELECT vi.id AS battleId, vi.title AS title, vo1.opinion AS opinion1, vo2.opinion AS opinion2, " +
		"(SELECT COUNT(*) FROM user_vote_opinion uvo WHERE uvo.vote_info_id = vi.id AND uvo.vote_info_index = 0) AS opinion1Count, " +
		"(SELECT COUNT(*) FROM user_vote_opinion uvo WHERE uvo.vote_info_id = vi.id AND uvo.vote_info_index = 1) AS opinion2Count, " +
		"uvo.vote_info_index AS userVote " +
		"FROM vote_info vi " +
		"JOIN vote_opinion vo1 ON vi.id = vo1.vote_info_id AND vo1.vote_opinion_index = 0 " +
		"JOIN vote_opinion vo2 ON vi.id = vo2.vote_info_id AND vo2.vote_opinion_index = 1 " +
		"LEFT JOIN user_vote_opinion uvo ON vi.id = uvo.vote_info_id AND uvo.user_id = 1 " +
		"ORDER BY vi.end_date DESC", nativeQuery = true)
	List<Object[]> findAllVotesWithCountsAndUserVoteStatus(@Param("userId") Long userId);
}
