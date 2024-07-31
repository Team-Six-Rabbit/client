package com.woowahanrabbits.battle_people.domain.user.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.PrincipalDetails;
import com.woowahanrabbits.battle_people.domain.user.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public User getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
			return ((PrincipalDetails)authentication.getPrincipal()).getUser();
		}
		return null;
	}
}
