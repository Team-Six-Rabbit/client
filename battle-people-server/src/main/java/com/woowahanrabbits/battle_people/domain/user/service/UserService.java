package com.woowahanrabbits.battle_people.domain.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.API.dto.APIResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtil jwtUtil;

	/*
	ResponseEntity
	.status(HttpStatus.OK)
	.body(new APIResponseDto<>("success", "JOIN SUCCESS", user));
	 */

	public Map<String, Object> login(LoginRequest loginRequest, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		result.put("response", response);
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		if (userOptional.isEmpty()) {
			result.put("responseEntity", ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new APIResponseDto<>("fail", "Email is Wrong", null)));
			return result;
		}

		User user = userOptional.get();

		if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			result.put("responseEntity", ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new APIResponseDto<>("fail", "Password is Wrong", null)));
			return result;
		}

		UserToken userToken = UserToken.builder()
			.accessToken(jwtUtil.generateAccessToken(user.getEmail()))
			.refreshToken(jwtUtil.generateRefreshToken(user.getEmail()))
			.user(user)
			.build();

		userTokenRepository.save(userToken);
		response.addCookie(createCookie("access", userToken.getAccessToken(), "/"));
		response.addCookie(createCookie("refresh", userToken.getRefreshToken(), "/auth/refresh"));

		result.put("responseEntity", ResponseEntity.ok(new APIResponseDto<>("success", "Login Successful", null)));
		result.put("response", response);
		// 쿠키 설정 추가 구현 필요
		return result;
	}

	public ResponseEntity<?> join(JoinRequest joinRequest) {
		String email = joinRequest.getEmail();
		String password = bCryptPasswordEncoder.encode(joinRequest.getPassword());
		String nickname = joinRequest.getNickname();

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "Email is exist", null));
		}

		if (userRepository.existsByNickname(nickname)) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "Nickname is exist", null));
		}

		User user = User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.rating(0)
			.build();

		userRepository.save(user);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new APIResponseDto<>("success", "User joined", userRepository.getUserIdByEmail(email)));
	}

	private Cookie createCookie(String name, String value, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath(path);
		cookie.setMaxAge(60 * 60);  // 1시간
		return cookie;
	}
}
