package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveApplyUser;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveVoiceRecord;
import com.woowahanrabbits.battle_people.domain.live.domain.Room;
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

@Service
public class OpenViduServiceImpl implements OpenViduService {

	private final OpenVidu openVidu;
	private final RoomRepository roomRepository;
	private final LiveApplyUserRepository liveApplyUserRepository;
	private final UserRepository userRepository;
	private final LiveVoiceRecordRepository liveVoiceRecordRepository;
	private final BattleBoardRepository battleBoardRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final Map<String, Session> sessions = new HashMap<>();
	private final Map<Long, String> userRecordings = new HashMap<>();

	public OpenViduServiceImpl(@Value("${openvidu.url}") String openviduUrl,
		@Value("${openvidu.secret}") String secret,
		RoomRepository roomRepository,
		LiveApplyUserRepository liveApplyUserRepository,
		UserRepository userRepository,
		LiveVoiceRecordRepository liveVoiceRecordRepository,
		BattleBoardRepository battleBoardRepository, UserVoteOpinionRepository userVoteOpinionRepository) {
		this.userVoteOpinionRepository = userVoteOpinionRepository;
		this.openVidu = new OpenVidu(openviduUrl, secret);
		this.roomRepository = roomRepository;
		this.liveApplyUserRepository = liveApplyUserRepository;
		this.userRepository = userRepository;
		this.liveVoiceRecordRepository = liveVoiceRecordRepository;
		this.battleBoardRepository = battleBoardRepository;
	}

	@Override
	public String createSession(Long battleId) throws OpenViduJavaClientException, OpenViduHttpException {
		// try {
		if (Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null)).getRoom() != null) {
			return Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null))
				.getRoom()
				.getRoomId();
		}
		BattleBoard battleBoard = Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null));
		System.out.println(battleBoard);
		Session session = openVidu.createSession();
		Room room = new Room();
		room.setRoomId(session.getSessionId());
		roomRepository.save(room);
		// BattleBoard battleBoard = Objects.requireNonNull(battleBoardRepository.findById(battleId).orElse(null));
		battleBoard.setRoom(room);
		battleBoardRepository.save(battleBoard);
		sessions.put(session.getSessionId(), session);
		return session.getSessionId();
		// } catch (OpenViduJavaClientException | OpenViduHttpException e) {
		// 	return null;
		// }
	}

	@Override
	public String getToken(String roomId, OpenViduRole role, Long userId) {
		try {
			Room room = roomRepository.findByRoomId(roomId);
			Session session = sessions.get(room.getRoomId());

			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			liveApplyUserRepository.save(LiveApplyUser.builder()
				.room(room)
				.participant(user)
				.role(role == OpenViduRole.PUBLISHER ? "broadcaster" : "viewer")
				.build());

			ConnectionProperties.Builder connectionPropertiesBuilder = new ConnectionProperties.Builder()
				.type(ConnectionType.WEBRTC)
				.role(role);

			if (role == OpenViduRole.PUBLISHER) {
				connectionPropertiesBuilder.record(true); // 발언자 스트림만 녹화 설정
				startRecording(roomId);

				Recording recording = startRecording(session.getSessionId());
				if (recording != null) {
					userRecordings.put(userId, recording.getId());
				}
			}

			return session.createConnection(connectionPropertiesBuilder.build()).getToken();
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return null;
		}
	}

	@Override
	public Recording startRecording(String roomId) {
		try {
			RecordingProperties recordingProperties = new RecordingProperties.Builder()
				.outputMode(Recording.OutputMode.INDIVIDUAL) // 개별 스트림 녹화 모드
				.build();
			return openVidu.startRecording(roomId, recordingProperties);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return null;
		}
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
	public Recording getRecording(String recordingId) {
		try {
			return openVidu.getRecording(recordingId);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return null;
		}
	}

	@Override
	public void userLeft(String roomId, Long userId) {
		Room room = roomRepository.findByRoomId(roomId);
		if (room == null) {
			throw new RuntimeException("Room not found");
		}

		LiveApplyUser liveApplyUser = liveApplyUserRepository.findByRoomIdAndParticipantId(room.getId(), userId);
		if (liveApplyUser == null) {
			throw new RuntimeException("User not found in room");
		}

		liveApplyUser.setOutTime(new Date());
		liveApplyUserRepository.save(liveApplyUser);
	}

	@Override
	public String changeRole(Long battleId, String roomId, Long userId) {
		try {
			LiveApplyUser liveApplyUser = liveApplyUserRepository.findByRoomIdAndParticipantId(
				roomRepository.findByRoomId(roomId).getId(), userId);

			liveApplyUser.setRole(liveApplyUser.getRole().equals("broadcaster") ? "viewer" : "broadcaster");
			liveApplyUserRepository.save(liveApplyUser);

			if (liveApplyUser.getRole().equals("publisher")) {
				String recordingId = userRecordings.get(userId);
				stopRecording(battleId, userId, recordingId);
				userRecordings.remove(userId);
			} else {
				Recording recording = startRecording(roomId);
				userRecordings.put(userId, recording.getId());
			}

			return getToken(roomId,
				liveApplyUser.getRole().equals("broadcaster") ? OpenViduRole.PUBLISHER : OpenViduRole.SUBSCRIBER,
				userId
			);

		} catch (Exception e) {
			return null;
		}
	}

}
