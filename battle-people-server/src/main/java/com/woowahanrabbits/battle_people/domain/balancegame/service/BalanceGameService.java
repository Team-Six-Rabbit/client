package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.balancegame.dto.AddBalanceGameCommentRequest;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;

public interface BalanceGameService {
	void addBalanceGame(CreateBalanceGameRequest createBalanceGameRequest, Long userId);

	List<BalanceGameResponse> getBalanceGameByConditions(Integer category, int status, int page, User user);

	void deleteBalanceGame(Long id, User user);

	List<?> getCommentsByBattleId(Long id);

	void addComment(AddBalanceGameCommentRequest addBalanceGameCommentRequest, User user);

	List<UserVoteOpinion> getUserVotelist(User user);

	BalanceGameResponse getBalanceGameById(Long id, User user);
}
