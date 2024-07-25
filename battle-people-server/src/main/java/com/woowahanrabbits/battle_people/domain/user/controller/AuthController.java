package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		Map<String, Object> map = userService.login(loginRequest, response);
		response = (HttpServletResponse) map.get("response");
		return (ResponseEntity<?>)map.get("responseEntity");
	}

	/*
	return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new APIResponseDto<>("success", "User joined", userRepository.getUserIdByEmail(email)));
	 */


}
