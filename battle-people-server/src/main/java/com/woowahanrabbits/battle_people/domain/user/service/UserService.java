package com.woowahanrabbits.battle_people.domain.user.service;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.UserCustom;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Boolean join(UserCustom userCustom) {
		// 입력 - UserCustom
		String email = userCustom.getEmail();
		String hashedPassword = bCryptPasswordEncoder.encode(userCustom.getPassword());
		String nickname = userCustom.getNickname();
		// String img_url = userCustom.getImg_url();
		// Integer rating = userCustom.getRating();
		// String access_token = userCustom.getAccess_token();
		// LocalDate penalty_start_date = userCustom.getPenalty_start_date();
		// LocalDate penalty_end_date = userCustom.getPenalty_end_date();

		Boolean isExist = userRepository.existsByEmail(email);
		if (isExist) {
			return false;
		}

		// Entity - User
		User user = new User();

		user.setEmail(email);
		user.setPassword(hashedPassword);
		user.setNickname(nickname);
		// user.setRating(rating);
		// user.setAccess_token(access_token);
		// user.setPenalty_start_date(penalty_start_date);
		// user.setPenalty_end_date(penalty_end_date);

		userRepository.save(user);
		return true;
	}
}
