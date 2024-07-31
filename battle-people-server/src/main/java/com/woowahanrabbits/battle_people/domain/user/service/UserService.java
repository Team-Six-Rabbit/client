package com.woowahanrabbits.battle_people.domain.user.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	public Map<String, Object> login(
		LoginRequest loginRequest, HttpServletResponse response);

	public ResponseEntity<?> join(JoinRequest joinRequest);

	public ResponseEntity<ApiResponseDto<?>> findAllUsers();

	ResponseEntity<ApiResponseDto<?>> getInterest(long id);

	ResponseEntity<ApiResponseDto<?>> setInterest(long userId, InterestRequest request);

	ResponseEntity<ApiResponseDto<?>> getUserProfile(long id);
}
