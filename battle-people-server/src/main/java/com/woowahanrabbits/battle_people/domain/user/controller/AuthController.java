package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;
import com.woowahanrabbits.battle_people.domain.user.service.impl.UserServiceImpl;
import com.woowahanrabbits.battle_people.util.HttpUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserServiceImpl userServiceImpl;
	private final UserTokenRepository userTokenRepository;
	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		Map<String, Object> map = userServiceImpl.login(loginRequest, response);
		response = (HttpServletResponse)map.get("response");
		return (ResponseEntity<?>)map.get("responseEntity");
	}

	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(@CookieValue("access") String access, HttpServletResponse response) {
		userTokenRepository.deleteByUserId(jwtUtil.extractUserId(access));

		HttpUtils.deleteCookies(
			response,
			HttpUtils.accessTokenRemovalCookie,
			HttpUtils.refreshTokenRemovalCookie
		);

		return ResponseEntity.ok(new ApiResponseDto<>("success", "Logout", null));
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@CookieValue(name = "refresh") String refresh, HttpServletResponse response) {
		try {
			jwtUtil.isTokenExpired(refresh);
		} catch (ExpiredJwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDto<>("fail", "Refresh expired", null));
		}

		long userId = jwtUtil.extractUserId(refresh);
		String username = jwtUtil.extractUsername(refresh);
		String userRole = jwtUtil.extractUserRole(refresh);
		String newAccess = jwtUtil.generateAccessToken(userId, username, userRole);

		userTokenRepository.updateAccessTokenByUserId(userId, newAccess);
		response.addCookie(HttpUtils.createCookie("access", newAccess, "/"));
		return ResponseEntity.ok(new ApiResponseDto<>("success", "Refresh", null));
	}
}
