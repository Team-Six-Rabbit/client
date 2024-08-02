package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;

import io.openvidu.java.client.OpenViduRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {
	private final OpenViduService openViduService;

	@PostMapping("/create-session")
	public ResponseEntity<ApiResponseDto<String>> createSession(@RequestParam Long battleId) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "", openViduService.createSession(battleId)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "", "fail"));
		}

	}

	@PostMapping("/get-token")
	public ResponseEntity<ApiResponseDto<String>> getToken(@RequestParam String roomId, @RequestParam String role,
		@RequestParam Long userId) {
		try {
			OpenViduRole openViduRole = "broadcaster".equals(role) ? OpenViduRole.PUBLISHER : OpenViduRole.SUBSCRIBER;
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "", openViduService.getToken(roomId, openViduRole, userId)));

		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "", "fail"));
		}
	}

	@PostMapping("/user-left")
	public ResponseEntity<ApiResponseDto<?>> userLeft(@RequestParam Long battleId, @RequestParam String roomId,
		@RequestParam Long userId) {
		try {
			openViduService.userLeft(battleId, roomId, userId);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>("fail", "", null));
		}
	}

	@PostMapping("/change-user-role")
	public ResponseEntity<ApiResponseDto<String>> changeUserRole(@RequestParam Long battleId,
		@RequestParam String roomId,
		@RequestParam Long userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "", openViduService.changeRole(battleId, roomId, userId)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "", ""));
		}
	}

}
