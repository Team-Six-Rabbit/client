package com.woowahanrabbits.battle_people.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.API.dto.APIResponseDto;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		userService.login(loginRequest);

		response.addCookie(createCookie("access", userService.getAccessToken(), "/"));
		response.addCookie(createCookie("refresh_token", userService.getRefreshToken(), "/auth/refresh"));

		return ResponseEntity.status(HttpStatus.OK).body(new APIResponseDto<>("success", "Login Success", null));
	}
	/*
	return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new APIResponseDto<>("success", "User joined", userRepository.getUserIdByEmail(email)));
	 */

	private Cookie createCookie(String name, String value, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath(path);
		cookie.setMaxAge(60 * 60);  // 1시간
		return cookie;
	}
}
