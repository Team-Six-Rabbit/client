package com.woowahanrabbits.battle_people.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	Boolean existsByEmail(String email);
	Long getUserIdByEmail(String email);
}
