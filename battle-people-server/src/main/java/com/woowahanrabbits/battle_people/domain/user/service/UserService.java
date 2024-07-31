package com.woowahanrabbits.battle_people.domain.user.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	public Map<String, Object> login(
		LoginRequest loginRequest, HttpServletResponse response);

	public ResponseEntity<?> join(JoinRequest joinRequest);

}
