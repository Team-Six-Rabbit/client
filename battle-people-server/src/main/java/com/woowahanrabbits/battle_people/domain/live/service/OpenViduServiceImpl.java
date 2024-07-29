package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.live.domain.Room;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.RoomRepository;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;

@Service
public class OpenViduServiceImpl implements OpenViduService {

	private final OpenVidu openVidu;
	private final RoomRepository roomRepository;
	private final Map<String, Session> sessions = new HashMap<>();

	public OpenViduServiceImpl(@Value("${openvidu.url}") String openviduUrl,
		@Value("${openvidu.secret}") String secret,
		RoomRepository roomRepository) {
		this.openVidu = new OpenVidu(openviduUrl, secret);
		this.roomRepository = roomRepository;
	}

	@Override
	public String createSession() {
		try {
			Session session = openVidu.createSession();
			Room room = new Room();
			room.setRoomId(session.getSessionId());
			roomRepository.save(room);
			sessions.put(session.getSessionId(), session);
			return session.getSessionId();
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return null;
		}
	}

	@Override
	public String getToken(String roomId, OpenViduRole role) {
		try {
			Room room = roomRepository.findByRoomId(roomId);
			if (room == null) {
				return null;
			}

			Session session = sessions.get(room.getRoomId());
			if (session == null) {
				return null;
			}

			ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
				.type(ConnectionType.WEBRTC)
				.role(role)
				.build();

			return session.createConnection(connectionProperties).getToken();
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return null;
		}
	}
}
