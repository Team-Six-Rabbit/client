package com.woowahanrabbits.battle_people.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.PrincipalDetails;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Service
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

		return new PrincipalDetails(userEntity);
	}
}
