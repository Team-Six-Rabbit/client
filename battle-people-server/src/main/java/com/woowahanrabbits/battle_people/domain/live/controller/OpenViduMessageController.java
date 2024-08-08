package com.woowahanrabbits.battle_people.domain.live.controller;

import org.springframework.stereotype.Controller;

import com.woowahanrabbits.battle_people.domain.live.service.OpenViduService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OpenViduMessageController {
	private final OpenViduService openViduService;

}
