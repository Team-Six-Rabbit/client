package com.woowahanrabbits.battle_people.domain.battle.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteInfoRepository extends JpaRepository<VoteInfo, Long> {
}
