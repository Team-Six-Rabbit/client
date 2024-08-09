package com.woowahanrabbits.battle_people.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;

import jakarta.transaction.Transactional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
	UserToken findUserTokenByUserId(Long userId);

	UserToken findByAccessToken(String accessToken);

	@Transactional
	void deleteByUserId(Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE UserToken u SET u.accessToken = :accessToken WHERE u.user.id = :userId")
	void updateAccessTokenByUserId(Long userId, String accessToken);
}
