package com.woowahanrabbits.battle_people.domain.user.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.woowahanrabbits.battle_people.domain.api.dto.APIResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<APIResponseDto<String>> handleAccessDeniedException(AccessDeniedException ex) {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "An error occurred: Access Denied", null);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponseDto<String>> handleException(Exception ex) throws
		IOException {
		APIResponseDto<String> response = new APIResponseDto<>("fail", "An error occurred: " + ex.getMessage(),
			null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
