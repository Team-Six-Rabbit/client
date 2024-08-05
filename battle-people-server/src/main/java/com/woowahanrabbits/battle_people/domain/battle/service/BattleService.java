package com.woowahanrabbits.battle_people.domain.battle.service;

import java.util.List;

import com.woowahanrabbits.battle_people.domain.battle.dto.AwaitingBattleResponseDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRespondRequest;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleResponse;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface BattleService {
	void registBattle(BattleInviteRequest battleInviteRequest, User user);

	List<BattleResponse> getReceivedBattleList(User user, int page, Long id);

	void acceptOrDeclineBattle(BattleRespondRequest battleRespondRequest, User user);

	List<AwaitingBattleResponseDto> getAwaitingBattleList(Integer category, int page, User user);

	int applyBattle(BattleApplyDto battleApplyDto, User user);

}
