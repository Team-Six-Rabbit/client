package com.woowahanrabbits.battle_people.domain.user.handler;

import org.springframework.security.core.AuthenticationException;

public class ExpiredJwtException extends AuthenticationException {
	public ExpiredJwtException(String message) {
		super(message);
	}
}
