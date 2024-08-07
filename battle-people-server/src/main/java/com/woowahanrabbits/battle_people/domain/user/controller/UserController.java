package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
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

	@GetMapping("/profile")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseDto<BasicUserDto>> getLoginUserProfile(@LoginUser User loginUser) {
		User user = userService.getUserProfile(loginUser.getId());
		BasicUserDto userDto = new BasicUserDto(user);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", userDto));
	}

	@GetMapping("/profile/{userId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseDto<BasicUserDto>> getUserProfile(@PathVariable(value = "userId") Long userId) {
		User user = userService.getUserProfile(userId);
		BasicUserDto userDto = new BasicUserDto(user);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", userDto));
	}

	@GetMapping("/interest")
	@PreAuthorize("isAuthenticated()")
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

	@GetMapping("/check/nickname")
	public ResponseEntity<ApiResponseDto<Boolean>> checkNickname(@RequestParam String nickname) {
		boolean isAvailable = userService.isNicknameAvailable(nickname);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "닉네임 확인", isAvailable));
	}

	@GetMapping("/check/email")
	public ResponseEntity<ApiResponseDto<Boolean>> checkEmail(@RequestParam String email) {
		boolean isAvailable = userService.isEmailAvailable(email);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "닉네임 확인", isAvailable));
	}
}
