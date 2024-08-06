package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveApplyUser;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveVoiceRecord;
import com.woowahanrabbits.battle_people.domain.live.domain.Room;
import com.woowahanrabbits.battle_people.domain.live.dto.OpenViduTokenResponseDto;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveVoiceRecordRepository;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.RoomRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;
import io.openvidu.java.client.RecordingProperties;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

@Service
public class OpenViduServiceImpl implements OpenViduService {

	private final OpenVidu openVidu;
	private final RoomRepository roomRepository;
	private final LiveApplyUserRepository liveApplyUserRepository;
	private final UserRepository userRepository;
	private final LiveVoiceRecordRepository liveVoiceRecordRepository;
	private final BattleBoardRepository battleBoardRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private final HashMap<String, Session> sessions = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(OpenViduServiceImpl.class);
	private static int count = 0;

	public OpenViduServiceImpl(@Value("${openvidu.url}") String openviduUrl,
		@Value("${openvidu.secret}") String secret,
		RoomRepository roomRepository,
		LiveApplyUserRepository liveApplyUserRepository,
		UserRepository userRepository,
		LiveVoiceRecordRepository liveVoiceRecordRepository,
		BattleBoardRepository battleBoardRepository, UserVoteOpinionRepository userVoteOpinionRepository,
		RedisTemplate<String, String> redisTemplate) {
		this.userVoteOpinionRepository = userVoteOpinionRepository;
		this.redisTemplate = redisTemplate;
		this.openVidu = new OpenVidu(openviduUrl, secret);
		this.roomRepository = roomRepository;
		this.liveApplyUserRepository = liveApplyUserRepository;
		this.userRepository = userRepository;
		this.liveVoiceRecordRepository = liveVoiceRecordRepository;
		this.battleBoardRepository = battleBoardRepository;
	}

	@Override
	public Session createSession(Long battleId) {
		System.out.println("------------------------------------------------------------------------");
		BattleBoard battleBoard = Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null));
		if (battleBoard == null) {
			return null;
		}
		Room room = roomRepository.findByRoomId(battleBoard.getRoom().getRoomId());
		if (room != null) {
			for (Session session : openVidu.getActiveSessions()) {
				System.out.println(
					"session id= " + session.getSessionId() + ", size= " + session.getActiveConnections().size());
				if (session.getSessionId().equals(room.getRoomId()) && !session.getActiveConnections().isEmpty()) {
					System.out.println("createSession method: [Exists Room] : " + room.getRoomId());
					return session;
				}
			}
		}

		Session session = null;
		try {
			RecordingProperties recordingProperties = new RecordingProperties.Builder()
				.outputMode(Recording.OutputMode.INDIVIDUAL) // 개별 스트림 녹화 모드
				.hasVideo(false)
				.build();
			SessionProperties properties = new SessionProperties.Builder()
				.customSessionId(battleId.toString())
				.defaultRecordingProperties(recordingProperties)
				.build();
			session = openVidu.createSession(properties);
		} catch (OpenViduJavaClientException e) {
			throw new RuntimeException(e);
		} catch (OpenViduHttpException e) {
			throw new RuntimeException(e);
		}

		room = new Room();
		room.setRoomId(session.getSessionId());
		roomRepository.save(room);

		logger.info("[create Session] room: " + room.getRoomId());

		battleBoard.setRoom(room);
		battleBoardRepository.save(battleBoard);

		Date endDate = battleBoard.getVoteInfo().getEndDate();
		long diffInMillis = endDate.getTime() - System.currentTimeMillis();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

		redisTemplate.opsForValue()
			.set("session:" + session.getSessionId(), session.getSessionId(), diffInSeconds, TimeUnit.SECONDS);
		sessions.put(session.getSessionId(), session);

		System.out.println(battleBoard);
		return session;
	}

	@Override
	public OpenViduTokenResponseDto getToken(Long battleId, User user) throws
		OpenViduJavaClientException, OpenViduHttpException {
		BattleBoard battleBoard = battleBoardRepository.findById(battleId).orElse(null);

		if (battleBoard == null) {
			return null;
		}

		Room room = battleBoard.getRoom();
		Session session = null;
		String roomId = null;
		if (room == null) {
			session = createSession(battleId);
		} else {
			roomId = room.getRoomId();
			for (Session s : openVidu.getActiveSessions()) {
				if (s.getSessionId().equals(roomId)) {
					session = s;
				}
			}
			if (session == null) {
				session = createSession(battleId);
			}

		}

		logger.info(
			"[getToken method] " + count++ + " roomId: " + session.getSessionId() + ", user name:" + user.getId());

		OpenViduRole role = OpenViduRole.SUBSCRIBER;
		int index = -1;

		if (battleBoard.getRegistUser().getId() == user.getId()) {
			role = OpenViduRole.PUBLISHER;
			index = 0;
		} else if (battleBoard.getOppositeUser().getId() == user.getId()) {
			role = OpenViduRole.PUBLISHER;
			index = 1;
		}
		logger.info(
			"[GetToken] room: " + roomId + ", userId: " + user.getId() + ", role:" + role + ", index: " + index);

		ConnectionProperties.Builder connectionPropertiesBuilder = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.role(role);

		String token = null;
		try {
			token = session.createConnection(connectionPropertiesBuilder.build()).getToken();
		} catch (OpenViduHttpException e) {
			session = createSession(battleId);
			room = roomRepository.findByRoomId(session.getSessionId());
			token = session.createConnection(connectionPropertiesBuilder.build()).getToken();
		}

		User managedUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new RuntimeException("User not found"));

		assert room != null;
		LiveApplyUser existingEntry = liveApplyUserRepository.findByRoomIdAndParticipantId(room.getId(),
			managedUser.getId());

		if (existingEntry != null) {
			logger.info("[User Token] :" + token + ", userid: " + existingEntry.getParticipantId());
			existingEntry.setToken(token);
			liveApplyUserRepository.save(existingEntry);
			return new OpenViduTokenResponseDto(existingEntry.getToken(), index);
		}

		// Save LiveApplyUser
		liveApplyUserRepository.save(LiveApplyUser.builder()
			.room(room)
			.participant(managedUser) // 영속성 컨텍스트 내에서 관리되는 user 객체
			.role(role == OpenViduRole.PUBLISHER ? "broadcaster" : "viewer")
			.token(token)
			.build());

		logger.info("[User Token] :" + token + ", userid: " + user.getId());
		return new OpenViduTokenResponseDto(token, index);

	}

	@Override
	public Recording startRecording(String roomId) throws OpenViduJavaClientException, OpenViduHttpException {
		RecordingProperties recordingProperties = new RecordingProperties.Builder()
			.outputMode(Recording.OutputMode.INDIVIDUAL) // 개별 스트림 녹화 모드
			.build();

		return openVidu.startRecording(roomId, recordingProperties);
	}

	@Override
	public boolean stopRecording(Long battleId, Long userId, String recordingId) {
		try {
			liveVoiceRecordRepository.save(LiveVoiceRecord.builder()
				.battleBoard(battleBoardRepository.findById(battleId).orElse(null))
				.user(userRepository.findById(userId).orElse(null))
				.selectedOpinion(userVoteOpinionRepository.findByUser_IdAndVoteInfo_Id(userId,
					Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null))
						.getVoteInfo()
						.getId()).getVoteInfoIndex())
				.recordUrl(openVidu.stopRecording(recordingId).getUrl())
				.build()
			);

			return true;
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return false;
		}
	}

	@Override
	public void userLeft(Long battleId, String roomId, Long userId) {
		Room room = roomRepository.findByRoomId(roomId);
		if (room == null) {
			throw new RuntimeException("Room not found");
		}

		LiveApplyUser liveApplyUser = liveApplyUserRepository.findByRoomIdAndParticipantId(room.getId(), userId);
		if (liveApplyUser == null) {
			throw new RuntimeException("User not found in room");
		}

		if (liveApplyUser.getRole().equals("broadcaster")) {
			stopRecording(battleId, userId, (String)redisTemplate.opsForValue().get("recording:" + userId));
		}

		liveApplyUser.setOutTime(new Date());
		liveApplyUserRepository.save(liveApplyUser);
	}

	// 대공사 예정
	// @Override
	// public OpenViduTokenResponseDto changeRole(String roomId, User user) {
	// 	try {
	// 		LiveApplyUser liveApplyUser = liveApplyUserRepository.findByRoomIdAndParticipantId(
	// 			roomRepository.findByRoomId(roomId).getId(), user.getId());
	//
	// 		liveApplyUser.setRole(liveApplyUser.getRole().equals("broadcaster") ? "viewer" : "broadcaster");
	// 		liveApplyUserRepository.save(liveApplyUser);
	//
	// 		if (liveApplyUser.getRole().equals("publisher")) {
	// 			String recordingId = (String)redisTemplate.opsForValue().get("recording:" + user.getId());
	// 			stopRecording(battleBoardRepository.findByRoomId(roomRepository.findByRoomId(roomId).getId()).getId(),
	// 				user.getId(), recordingId);
	// 			redisTemplate.delete("recording:" + user.getId());
	// 		} else {
	// 			Recording recording = startRecording(roomId);
	// 			redisTemplate.opsForValue().set("recording:" + user.getId(), recording.getId(), 24, TimeUnit.HOURS);
	// 		}
	//
	// 		return getToken(roomId, user);
	//
	// 	} catch (Exception e) {
	// 		return null;
	// 	}
	// }

}
