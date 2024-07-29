package com.woowahanrabbits.battle_people.domain.balancegame.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameCommentDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;

public interface BalanceGameService {
	void addBalanceGame(CreateBalanceGameRequest createBalanceGameRequest, int userId);

	Page<BalanceGameResponse> getBalanceGameByConditions(Integer category, int status, int page, User user);

	void deleteBalanceGame(Long id, User user);

	Page<?> getCommentsByBattleId(Long id, int page, int totalPage);

	void addComment(BalanceGameCommentDto balanceGameCommentDto);

	List<UserVoteOpinion> getUserVotelist(User user);
}
