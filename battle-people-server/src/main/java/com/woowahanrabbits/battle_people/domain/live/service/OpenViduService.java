package com.woowahanrabbits.battle_people.domain.live.service;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;

public interface OpenViduService {
	String createSession(Long battleId) throws OpenViduJavaClientException, OpenViduHttpException;

	void userLeft(Long battleId, String roomId, Long userId);

	String getToken(String roomId, OpenViduRole role, Long userId) throws
		OpenViduJavaClientException,
		OpenViduHttpException;

	Recording startRecording(String roomId) throws OpenViduJavaClientException, OpenViduHttpException;

	boolean stopRecording(Long battleId, Long userId, String recordingId);

	String changeRole(Long battleId, String roomId, Long userId);
}
