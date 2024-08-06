package com.woowahanrabbits.battle_people.domain.live.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.live.domain.LiveApplyUser;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveApplyUserId;

public interface LiveApplyUserRepository extends JpaRepository<LiveApplyUser, LiveApplyUserId> {

	List<LiveApplyUser> findAllByRoom_Id(Long id);

	LiveApplyUser findByRoomIdAndParticipantId(Long roomId, Long participantId);
}
