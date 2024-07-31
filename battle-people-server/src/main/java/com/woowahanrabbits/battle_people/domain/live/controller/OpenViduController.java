package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;

import io.openvidu.java.client.OpenViduRole;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openvidu")
public class OpenViduController {
	private final OpenViduService openViduService;

	@PostMapping("/create-session")
	public String createSession(@RequestParam Long battleId) {
		try {
			return openViduService.createSession(battleId);
		} catch (Exception e) {
			return "session null";
		}

	}

	@PostMapping("/get-token")
	public String getToken(@RequestParam String roomId, @RequestParam String role, @RequestParam Long userId) {
		try {
			OpenViduRole openViduRole = "broadcaster".equals(role) ? OpenViduRole.PUBLISHER : OpenViduRole.SUBSCRIBER;
			return openViduService.getToken(roomId, openViduRole, userId);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@PostMapping("/user-left")
	public ResponseEntity<Void> userLeft(@RequestParam String roomId, @RequestParam Long userId) {
		try {
			openViduService.userLeft(roomId, userId);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/change-user-role")
	public String changeUserRole(@RequestParam Long battleId, @RequestParam String roomId, @RequestParam Long userId) {
		try {
			return openViduService.changeRole(battleId, roomId, userId);
		} catch (Exception e) {
			return null;
		}
	}

}
