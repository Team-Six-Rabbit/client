package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.API.dto.APIResponseDto;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;
import com.woowahanrabbits.battle_people.util.HttpUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;
	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		Map<String, Object> map = userService.login(loginRequest, response);
		response = (HttpServletResponse)map.get("response");
		return (ResponseEntity<?>)map.get("responseEntity");
	}

	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		// String userEmail = jwtUtil.extractUsername(accessToken);
		// userTokenRepository.deleteByUserId(user.getId());

		// 쿠키 삭제
		HttpUtils.deleteCookies(
			response,
			HttpUtils.accessTokenRemovalCookie,
			HttpUtils.refreshTokenRemovalCookie
		);

		return ResponseEntity.ok(new APIResponseDto<>("success", "Logout", null));
	}
}
