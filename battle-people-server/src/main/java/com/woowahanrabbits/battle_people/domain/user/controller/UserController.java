package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.service.impl.UserService;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;
import com.woowahanrabbits.battle_people.domain.user.resolver.LoginUsers;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody JoinRequest request) {
		return userService.join(request);
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<?>> getAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/profile")
	public ResponseEntity<ApiResponseDto<?>> getLoginUserProfile(@LoginUsers User user) {
		return userService.getUserProfile(user.getId());
	}

	@GetMapping("/profile/{userId}")
	public ResponseEntity<ApiResponseDto<?>> getUserProfile(@PathVariable(value = "userId") Long userId) {
		return userService.getUserProfile(userId);
	}

	@GetMapping("/interest")
	public ResponseEntity<ApiResponseDto<?>> getUserInterest(@LoginUsers User user) {
		return userService.getInterest(user.getId());
	}

	@PostMapping("/interest")
	public ResponseEntity<ApiResponseDto<?>> setUserInterest(@LoginUsers User user,
		@RequestBody InterestRequest request) {
		System.out.println(request.getCategory());
		return userService.setInterest(user.getId(), request);
	}
}
