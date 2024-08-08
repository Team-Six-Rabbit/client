package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.OpenViduTokenResponseDto;
import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.resolver.LoginUser;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import io.openvidu.java.client.OpenViduException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {
	private final OpenViduService openViduService;
	private final UserService userService;

	@PostMapping("/get-token")
	public ResponseEntity<ApiResponseDto<OpenViduTokenResponseDto>> getToken(@RequestParam Long battleId,
		@LoginUser User user) throws OpenViduException {
		System.out.println("User: " + user);
		return ResponseEntity.status(HttpStatus.OK)
			.body(new ApiResponseDto<>("success", "",
				openViduService.getToken(battleId, user)));
	}

	@PostMapping("/user-left")
	public ResponseEntity<ApiResponseDto<?>> userLeft(@RequestParam Long battleId, @RequestParam String roomId,
		@RequestParam Long userId) {
		openViduService.userLeft(battleId, userId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponseDto<?>> handleException(Exception exception) {
		exception.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>("fail", "", null));
	}
}
