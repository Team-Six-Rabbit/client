package com.woowahanrabbits.battle_people.domain.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.interest.domain.Interest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.InterestRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;
import com.woowahanrabbits.battle_people.util.HttpUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements com.woowahanrabbits.battle_people.domain.user.service.UserService {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;
	private final InterestRepository interestRepository;
	private final JwtUtil jwtUtil;

	@Override
	public Map<String, Object> login(LoginRequest loginRequest, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		result.put("response", response);
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		if (userOptional.isEmpty()) {
			result.put("responseEntity", ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiResponseDto<>("fail", "Email is Wrong", null)));
			return result;
		}

		User user = userOptional.get();

		if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			result.put("responseEntity", ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDto<>("fail", "Password is Wrong", null)));
			return result;
		}

		String access = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
		UserToken userToken = UserToken.builder()
			.accessToken(access)
			.refreshToken(jwtUtil.generateRefreshToken(user.getId(), user.getEmail(), user.getRole()))
			.user(user)
			.build();

		userTokenRepository.save(userToken);
		response.addCookie(HttpUtils.createCookie("access", userToken.getAccessToken(), "/"));
		response.addCookie(
			HttpUtils.createCookie("refresh", userToken.getRefreshToken(), "/battle-people/auth/refresh"));

		String userId = String.valueOf(jwtUtil.extractUserId(userToken.getAccessToken()));
		result.put("responseEntity", ResponseEntity.ok(
			new ApiResponseDto<>("success", "Login Successful", "userId : " + userId)));
		result.put("response", response);
		return result;
	}

	@Override
	public ResponseEntity<?> join(JoinRequest joinRequest) {
		String email = joinRequest.getEmail();
		String password = bCryptPasswordEncoder.encode(joinRequest.getPassword());
		String nickname = joinRequest.getNickname();

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "Email is exist", null));
		}

		if (userRepository.existsByNickname(nickname)) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "Nickname is exist", null));
		}

		User user = User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.rating(0)
			.role("ROLE_USER")
			.build();

		userRepository.save(user);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ApiResponseDto<>("success", "User joined", userRepository.getUserIdByEmail(email)));
	}

	@Override
	public ResponseEntity<ApiResponseDto<?>> findAllUsers() {
		List<User> list = userRepository.findAll();
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiResponseDto<>("success", "user is Empty", null));
		}
		return ResponseEntity.ok(new ApiResponseDto<>("success", "userList", list));
	}

	@Override
	public ResponseEntity<ApiResponseDto<?>> getInterest(long id) {
		List<Interest> list = interestRepository.findAllByUserId(id);
		List<Integer> response = new ArrayList<>();
		for (Interest interest : list) {
			response.add(interest.getCategory());
		}
		Collections.sort(response);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "interestList", response));
	}

	@Override
	public ResponseEntity<ApiResponseDto<?>> setInterest(long userId, InterestRequest request) {
		List<Integer> list = request.getCategory();
		for (int category : list) {
			Interest interest = Interest.builder()
				.userId(userId)
				.count(1)
				.category(category)
				.build();
			interestRepository.save(interest);
		}
		return ResponseEntity.ok(new ApiResponseDto<>("success", "Create User Interest", null));
	}

	@Override
	public ResponseEntity<ApiResponseDto<?>> getUserProfile(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(new ApiResponseDto<>("fail", "NOT EXIST USER", null));
		}
		return ResponseEntity.ok(new ApiResponseDto<>("success", "user", user));
	}

}
