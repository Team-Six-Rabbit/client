package com.woowahanrabbits.battle_people.domain.live.service;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;

public interface OpenViduService {
	String createSession();

	void userLeft(String roomId, Long userId);

	String getToken(String roomId, OpenViduRole role, Long userId);

	Recording startRecording(String roomId);

	boolean stopRecording(Long battleId, Long userId, String recordingId);

	Recording getRecording(String recordingId);

	String changeRole(Long battleId, String roomId, Long userId);
}
