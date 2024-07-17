package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.io.Serializable;

import lombok.Data;

import java.io.Serializable;

@Data
public class BattleApplyUserId implements Serializable {

    private Long user;
    private Long battleBoard;

    // 기본 생성자
    public BattleApplyUserId() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BattleApplyUserId that = (BattleApplyUserId) o;


        if (!user.equals(that.user)) return false;
        return battleBoard.equals(that.battleBoard);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + battleBoard.hashCode();
        return result;
    }
}
