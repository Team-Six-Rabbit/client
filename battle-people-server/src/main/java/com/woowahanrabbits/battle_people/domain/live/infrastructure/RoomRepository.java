package com.woowahanrabbits.battle_people.domain.live.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.live.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByRoomId(String roomId);
}
