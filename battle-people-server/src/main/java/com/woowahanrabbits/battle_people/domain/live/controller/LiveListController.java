package com.woowahanrabbits.battle_people.domain.live.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.APIResponseDto;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveEndDetailDto;
import com.woowahanrabbits.battle_people.domain.live.dto.LiveListResponseDto;
import com.woowahanrabbits.battle_people.domain.live.service.LiveListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/live")
public class LiveListController {
	private final LiveListService liveListService;

	@GetMapping("/active/list")
	public ResponseEntity<APIResponseDto<List<LiveListResponseDto>>> getActiveLiveList(
		@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
		@RequestParam(value = "category", required = false, defaultValue = "") String category,
		@PageableDefault(page = 0, size = 10, sort = "maxPeopleCount") Pageable pageable) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new APIResponseDto<>("success", "",
					liveListService.getActiveLiveList(keyword, category, pageable).getContent()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/wait/list")
	public ResponseEntity<APIResponseDto<List<LiveListResponseDto>>> getWaitLiveList(
		@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
		@RequestParam(value = "category", required = false, defaultValue = "") String category,
		@PageableDefault(page = 0, size = 10, sort = "maxPeopleCount") Pageable pageable) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new APIResponseDto<>("success", "",
					liveListService.getWaitLiveList(keyword, category, pageable).getContent()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/end/list")
	public ResponseEntity<APIResponseDto<List<LiveListResponseDto>>> getEndBattleBoards(
		@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
		@RequestParam(value = "category", required = false, defaultValue = "") String category,
		@PageableDefault(page = 0, size = 10, sort = "maxPeopleCount") Pageable pageable) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new APIResponseDto<>("success", "",
					liveListService.getEndLiveList(keyword, category, pageable).getContent()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/wait/detail/{battle_id}")
	public ResponseEntity<APIResponseDto<LiveListResponseDto>> getWaitLiveInfo(
		@PathVariable("battle_id") Long battleId) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new APIResponseDto<>("success", "", liveListService.getLiveInfo(battleId)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/end/detail/{battle_id}")
	public ResponseEntity<APIResponseDto<LiveEndDetailDto>> getEndLiveInfo(@PathVariable("battle_id") Long battleId) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
				.body(new APIResponseDto<>("success", "", liveListService.getEndLiveSummary(battleId)));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new APIResponseDto<>("fail", "internal server error", null));
		}
	}
}
