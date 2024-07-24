package com.woowahanrabbits.battle_people.domain.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	// @PostMapping("/login")
	// public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
	//
	// }
}
