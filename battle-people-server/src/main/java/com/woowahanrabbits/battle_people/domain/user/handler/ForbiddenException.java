package com.woowahanrabbits.battle_people.domain.user.handler;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
		super(message);
	}
}
