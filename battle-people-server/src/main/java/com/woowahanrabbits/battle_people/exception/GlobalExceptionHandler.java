package com.woowahanrabbits.battle_people.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponseDto> handleRuntimeException(RuntimeException error) {
		ApiResponseDto res = new ApiResponseDto("error", error.getMessage(), null);
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException error) {
		ApiResponseDto<String> response = new ApiResponseDto<>("error", error.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<String>> handleException(Exception error) {
		ApiResponseDto<String> response = new ApiResponseDto<>("error", "An unexpected error occurred",
			error.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
