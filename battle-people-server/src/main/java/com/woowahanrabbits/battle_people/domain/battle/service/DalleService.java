package com.woowahanrabbits.battle_people.domain.battle.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.woowahanrabbits.battle_people.domain.battle.domain.BattleBoard;
import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInfoDto;
import com.woowahanrabbits.battle_people.domain.battle.dto.ThumbnailRequestDto;
import com.woowahanrabbits.battle_people.domain.battle.infrastructure.BattleRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteOpinionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DalleService {

	private final BattleRepository battleRepository;
	private final VoteOpinionRepository voteOpinionRepository;
	private final VoteInfoRepository voteInfoRepository;
	@Value("${min.people.count.value}")
	private Integer minPeopleCount;

	@Value("${openai.api.key}")
	private String apikey;

	@Value("${storage.location}")
	private String uploadDir;

	private static final String DALL_E_API_URL = "https://api.openai.com/v1/images/generations";

	@Async
	public CompletableFuture<String> generateImageAsync(BattleInfoDto prompt) throws Exception {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return generateImage(prompt);
			} catch (Exception e) {
				throw new RuntimeException("이미지 생성 실패", e);
			}
		});
	}

	public String generateImage(BattleInfoDto prompt) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ThumbnailRequestDto requestDto = new ThumbnailRequestDto(prompt);

		// HTTP Headers 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apikey);

		// RequestEntity 생성
		HttpEntity<ThumbnailRequestDto> request = new HttpEntity<>(requestDto, headers);

		// POST 요청 보내기
		ResponseEntity<String> response = restTemplate.postForEntity(DALL_E_API_URL, request, String.class);

		// 응답 처리
		if (response.getStatusCode() == HttpStatus.OK) {
			// 응답에서 이미지 URL을 추출
			JSONObject jsonResponse = new JSONObject(response.getBody());
			JSONArray data = jsonResponse.getJSONArray("data");
			String imageUrl = data.getJSONObject(0).getString("url");

			BattleBoard battleBoard = prompt.getBattleBoard();
			System.out.println(imageUrl);

			// 이미지를 로컬 파일 시스템에 저장
			String savedFilePath = saveImageToLocal(imageUrl, battleBoard);

			battleBoard.setImageUrl(savedFilePath);
			battleRepository.save(battleBoard);

			return savedFilePath;

		}
		return null;
	}

	private String saveImageToLocal(String imageUrl, BattleBoard battleBoard) throws Exception {
		// URL 객체 생성
		URL url = new URL(imageUrl);
		String fileName = battleBoard.getId() + ".jpg";  // 예: "13.jpg"
		String filePath = uploadDir + File.separator
			+ "thumbnail";  // 예: "uploadDir/thumbnail"

		// 파일 경로를 확인하고 디렉토리가 존재하지 않으면 생성
		File directory = new File(filePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File file = new File(directory, fileName);

		// 이미지 다운로드 및 파일 저장
		try (InputStream in = url.openStream(); FileOutputStream out = new FileOutputStream(file)) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
		}
		return filePath + File.separator + Long.toString(battleBoard.getId()) + ".jpg"; // 저장된 파일 이름 반환
	}
}
