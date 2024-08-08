package com.woowahanrabbits.battle_people.domain.live.service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleBoardRepository;
import com.woowahanrabbits.battle_people.domain.live.domain.LiveApplyUser;
import com.woowahanrabbits.battle_people.domain.live.dto.OpenViduTokenResponseDto;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveApplyUserRepository;
import com.woowahanrabbits.battle_people.domain.live.infrastructure.LiveVoiceRecordRepository;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.UserVoteOpinionRepository;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;
import io.openvidu.java.client.RecordingProperties;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

@Service
public class OpenViduServiceImpl implements OpenViduService {
	enum PublisherRole {
		SPEAKER, SUPPORTER
	}

	record ServerData(int index, PublisherRole role) {
	}

	public static record RedisBattleDto(long battleId, long registerUserId, long oppositeUserId, Date endDate) {
	}

	private final OpenVidu openVidu;
	private final LiveApplyUserRepository liveApplyUserRepository;
	private final UserRepository userRepository;
	private final LiveVoiceRecordRepository liveVoiceRecordRepository;
	private final BattleBoardRepository battleBoardRepository;
	private final UserVoteOpinionRepository userVoteOpinionRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(OpenViduServiceImpl.class);
	private final ObjectMapper mapper;

	public OpenViduServiceImpl(@Value("${openvidu.url}") String openviduUrl,
		@Value("${openvidu.secret}") String secret,
		LiveApplyUserRepository liveApplyUserRepository,
		UserRepository userRepository,
		LiveVoiceRecordRepository liveVoiceRecordRepository,
		BattleBoardRepository battleBoardRepository, UserVoteOpinionRepository userVoteOpinionRepository,
		RedisTemplate<String, String> redisTemplate,
		ObjectMapper mapper) {
		this.userVoteOpinionRepository = userVoteOpinionRepository;
		this.redisTemplate = redisTemplate;
		this.openVidu = new OpenVidu(openviduUrl, secret);
		this.liveApplyUserRepository = liveApplyUserRepository;
		this.userRepository = userRepository;
		this.liveVoiceRecordRepository = liveVoiceRecordRepository;
		this.battleBoardRepository = battleBoardRepository;
		this.mapper = mapper;
	}

	@Override
	public Session createSession(BattleBoard battleBoard) {
		RecordingProperties recordingProperties = new RecordingProperties.Builder()
			.outputMode(Recording.OutputMode.INDIVIDUAL) // 개별 스트림 녹화 모드
			.hasVideo(false)
			.build();
		SessionProperties properties = new SessionProperties.Builder()
			.customSessionId(battleBoard.getId().toString())
			.defaultRecordingProperties(recordingProperties)
			.build();

		Session session = null;
		try {
			session = openVidu.createSession(properties);
		} catch (OpenViduException e) {
			throw new RuntimeException(e);
		}

		Date endDate = battleBoard.getVoteInfo().getEndDate();
		long diffInMillis = endDate.getTime() - System.currentTimeMillis();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

		// redisTemplate.opsForHash()
		// 	.put("battle", battleBoard.getId(),
		// 		new RedisBattleDto(battleBoard.getId(), battleBoard.getRegistUser().getId(),
		// 			battleBoard.getOppositeUser().getId(), battleBoard.getVoteInfo().getEndDate()));
		// redisTemplate.opsForValue()
		// 	.set("session:", session.getSessionId(), diffInSeconds, TimeUnit.SECONDS);

		return session;
	}

	@Override
	public OpenViduTokenResponseDto getToken(Long battleId, User user) {
		BattleBoard battleBoard = battleBoardRepository.findById(battleId).orElseThrow(NoSuchElementException::new);

		Session session = createSession(battleBoard);

		OpenViduRole role = OpenViduRole.SUBSCRIBER;
		int index = -1;

		if (battleBoard.getRegistUser().getId() == user.getId()) {
			role = OpenViduRole.PUBLISHER;
			index = 0;
		} else if (battleBoard.getOppositeUser().getId() == user.getId()) {
			role = OpenViduRole.PUBLISHER;
			index = 1;
		}

		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(getServerData(index))
			.role(role)
			.build();

		String token;
		try {
			token = session.createConnection(connectionProperties).getToken();
		} catch (OpenViduException exception) {
			throw new RuntimeException("Failed to create token", exception);
		}

		// Save LiveApplyUser
		// liveApplyUserRepository.save(LiveApplyUser.builder()
		// 	.battleId(battleId)
		// 	.participant(userRepository.getReferenceById(user.getId())) // 영속성 컨텍스트 내에서 관리되는 user 객체
		// 	.role(role == OpenViduRole.PUBLISHER ? "broadcaster" : "viewer")
		// 	.token(token)
		// 	.build());

		logger.info("[User Token] :" + token + ", userid: " + user.getId());
		return new OpenViduTokenResponseDto(token, index);
	}

	private String getServerData(int index) {
		String data = null;
		if (index >= 0) {
			try {
				data = mapper.writeValueAsString(new ServerData(index, PublisherRole.SPEAKER));
			} catch (JsonProcessingException ignored) {
			}
		}
		return data;
	}

	@Override
	public void userLeft(Long battleId, Long userId) {
		LiveApplyUser liveApplyUser = liveApplyUserRepository.findByBattleIdAndParticipantId(battleId, userId);
		if (liveApplyUser == null) {
			throw new RuntimeException("User not found in room");
		}

		liveApplyUser.setOutTime(new Date());
		liveApplyUserRepository.save(liveApplyUser);
	}

}
