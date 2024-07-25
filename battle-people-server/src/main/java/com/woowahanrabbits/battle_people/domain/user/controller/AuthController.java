package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.API.dto.APIResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refresh".equals(cookie.getName()) || "access".equals(cookie.getName())) {
					String token = cookie.getValue();
					String userEmail = jwtUtil.extractUsername(token);

					Optional<User> user = userRepository.findByEmail(userEmail);
					if (user.isPresent()) {
						userTokenRepository.deleteByUserId(user.get().getId());
					}
					// 쿠키 삭제
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			return ResponseEntity.ok(new APIResponseDto<>("success", "Logout", null));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponseDto<>("fail", "Cookie is Null", null));
	}

}
