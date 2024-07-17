package com.woowahanrabbits.battle_people.domain.battle.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class BattleApplyUserId implements Serializable {
    private Long battleBoardId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BattleApplyUserId that = (BattleApplyUserId) o;

        if (!userId.equals(that.userId)) return false;
        return battleBoardId.equals(that.battleBoardId);
    }

}
