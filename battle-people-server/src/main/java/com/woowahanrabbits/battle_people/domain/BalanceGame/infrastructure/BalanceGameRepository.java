package com.woowahanrabbits.battle_people.domain.balancegame.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.balancegame.domain.BalanceGameBoardComment;

public interface BalanceGameRepository extends JpaRepository<BalanceGameBoardComment, Long> {
}
