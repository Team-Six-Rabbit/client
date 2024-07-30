package com.woowahanrabbits.battle_people.domain.user.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.woowahanrabbits.battle_people.domain.api.dto.APIResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JwtAuthenticationException.class)
	public ResponseEntity<APIResponseDto<String>> handleJwtAuthenticationException(JwtAuthenticationException ex,
		WebRequest request) {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "Unauthorized", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<APIResponseDto<String>> handleExpiredJwtException(ExpiredJwtException ex,
		WebRequest request) {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "Unauthorized",
			"JWT expired: " + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponseDto<String>> handleGlobalException(Exception ex, WebRequest request) {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "Internal Server Error", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<APIResponseDto<String>> handleForbiddenException(ForbiddenException ex, WebRequest request) {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "Forbidden", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
}
