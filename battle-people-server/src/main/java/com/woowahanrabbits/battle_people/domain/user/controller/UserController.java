package com.woowahanrabbits.battle_people.domain.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.service.impl.UserService;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody JoinRequest request) {
		return userService.join(request);
	}

	@GetMapping
	public List<?> getAllUsers() {
		return null;
	}
}
