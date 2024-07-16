package com.woowahanrabbits.battle_people.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woowahanrabbits.battle_people.domain.user.dto.UserCustom;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

@Controller
@ResponseBody
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/join")
	public String joinProcess(UserCustom userCustom) {
		if (userService.join(userCustom)) {
			return "JOIN SUCCESS";
		}
		return "JOIN FAILED";
	}

}
