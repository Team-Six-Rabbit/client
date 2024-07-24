package com.woowahanrabbits.battle_people.domain.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsByNickname(String nickname);

	@Query("SELECT u.id FROM User u WHERE u.email = :email")
	Long getUserIdByEmail(String email);
}
