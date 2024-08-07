package com.woowahanrabbits.battle_people.domain.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.interest.domain.Interest;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.LoginRequest;
import com.woowahanrabbits.battle_people.domain.user.handler.UserException;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.InterestRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;
import com.woowahanrabbits.battle_people.domain.user.jwt.JwtUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;
	private final InterestRepository interestRepository;
	private final JwtUtil jwtUtil;

	public User login(LoginRequest loginRequest) {
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

		User user = userOptional.orElseThrow(() -> new UserException("Invalid email or password"));

		if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new UserException("Wrong password");
		}

		return user;
	}

	public User join(JoinRequest joinRequest) {
		String email = joinRequest.getEmail();
		String password = bCryptPasswordEncoder.encode(joinRequest.getPassword());
		String nickname = joinRequest.getNickname();

		if (userRepository.existsByEmail(email)) {
			throw new UserException("Email already in use");
		}

		if (userRepository.existsByNickname(nickname)) {
			throw new UserException("Nickname already in use");
		}

		User user = new User(email, password, nickname, nickname, "ROLE_USER");

		userRepository.save(user);

		return user;
	}

	public List<User> findAllUsers() {
		List<User> list = userRepository.findAll();
		if (list.isEmpty()) {
			throw new UserException("No users found");
		}
		return list;
	}

	public List<Integer> getInterest(long id) {
		List<Interest> list = interestRepository.findAllByUserId(id);
		List<Integer> response = new ArrayList<>();
		for (Interest interest : list) {
			response.add(interest.getCategory());
		}
		Collections.sort(response);
		return response;
	}

	public void setInterest(long userId, InterestRequest request) {
		List<Integer> list = request.getCategory();
		for (int category : list) {
			Interest interest = new Interest(userId, category, 1);
			interestRepository.save(interest);
		}
	}

	public User getUserProfile(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserException("User not found");
		}
		return user.get();
	}

	public boolean isNicknameAvailable(String nickname) {
		return !userRepository.existsByNickname(nickname);
	}

	public boolean isEmailAvailable(String email) {
		return !userRepository.existsByEmail(email);
	}

	public Optional<User> findByNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}
}
