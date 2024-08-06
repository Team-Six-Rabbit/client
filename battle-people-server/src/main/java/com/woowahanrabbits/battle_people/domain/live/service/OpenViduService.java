package com.woowahanrabbits.battle_people.domain.live.service;

import com.woowahanrabbits.battle_people.domain.live.dto.OpenViduTokenResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Recording;
import io.openvidu.java.client.Session;

public interface OpenViduService {
	Session createSession(Long battleId) throws OpenViduJavaClientException, OpenViduHttpException;

	void userLeft(Long battleId, String roomId, Long userId);

	OpenViduTokenResponseDto getToken(Long battleId, User user) throws
		OpenViduJavaClientException, OpenViduHttpException;

	Recording startRecording(String roomId) throws OpenViduJavaClientException, OpenViduHttpException;

	boolean stopRecording(Long battleId, Long userId, String recordingId);

	// OpenViduTokenResponseDto changeRole(String roomId, User user);
}
