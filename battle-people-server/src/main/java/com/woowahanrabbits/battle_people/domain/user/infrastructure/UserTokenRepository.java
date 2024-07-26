package com.woowahanrabbits.battle_people.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;

import jakarta.transaction.Transactional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	UserToken findUserTokenByUserId(Long userId);
	@Transactional
	void deleteByUserId(Long userId);
}
