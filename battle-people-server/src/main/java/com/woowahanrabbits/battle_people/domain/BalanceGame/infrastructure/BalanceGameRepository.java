package com.woowahanrabbits.battle_people.domain.BalanceGame.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.BalanceGame.domain.BalanceGameBoardComment;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;

public interface BalanceGameRepository extends JpaRepository<BalanceGameBoardComment, Long> {
}
