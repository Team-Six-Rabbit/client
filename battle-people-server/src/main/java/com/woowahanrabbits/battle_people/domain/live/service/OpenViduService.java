package com.woowahanrabbits.battle_people.domain.live.service;

import io.openvidu.java.client.OpenViduRole;

public interface OpenViduService {
	String createSession();

	String getToken(String roomId, OpenViduRole role);
}
