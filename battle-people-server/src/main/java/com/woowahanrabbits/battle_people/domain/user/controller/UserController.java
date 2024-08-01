package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.woowahanrabbits.battle_people.domain.user.resolver.LoginUser;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<ApiResponseDto<User>> join(@RequestBody JoinRequest request) {
		User user = userService.join(request);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User joined", user));
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<User>>> getAllUsers() {
		List<User> list = userService.findAllUsers();
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User list", list));
	}

	@GetMapping("/profile")
	public ResponseEntity<ApiResponseDto<User>> getLoginUserProfile(@LoginUser User loginUser) {
		User user = userService.getUserProfile(loginUser.getId());
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", user));
	}

	@GetMapping("/profile/{userId}")
	public ResponseEntity<ApiResponseDto<User>> getUserProfile(@PathVariable(value = "userId") Long userId) {
		User user = userService.getUserProfile(userId);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", user));
	}

	@GetMapping("/interest")
	public ResponseEntity<ApiResponseDto<Map<String, List<Integer>>>> getUserInterest(@LoginUser User user) {
		List<Integer> list = userService.getInterest(user.getId());
		Map<String, List<Integer>> response = new HashMap<>();
		response.put("category", list);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User interest", response));
	}

	@PostMapping("/interest")
	public ResponseEntity<ApiResponseDto<Void>> setUserInterest(@LoginUser User user,
		@RequestBody InterestRequest request) {
		userService.setInterest(user.getId(), request);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "Create User Category", null));
	}
}
