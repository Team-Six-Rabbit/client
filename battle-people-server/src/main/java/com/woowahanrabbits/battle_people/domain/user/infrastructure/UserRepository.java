package com.woowahanrabbits.battle_people.domain.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woowahanrabbits.battle_people.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

