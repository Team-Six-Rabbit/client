package com.woowahanrabbits.battle_people.domain.battle.domain;

import lombok.Data;

@Data
public class BattleApplyUserId {
    private Long userId;
    private Long voteInfoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BattleApplyUserId that = (BattleApplyUserId) o;

        if (!userId.equals(that.userId)) return false;
        return voteInfoId.equals(that.voteInfoId);
    }

}
