package com.woowahanrabbits.battle_people.domain.balancegame.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameCommentDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameReturnDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.balancegame.service.BalanceGameService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.vote.domain.UserVoteOpinion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/balance-game")
@Tag(name = "BalanceGameController", description = "밸런스게임 컨트롤러")
public class BalanceGameController {

	private final BalanceGameService balanceGameService;

	public BalanceGameController(BalanceGameService balanceGameService) {
		this.balanceGameService = balanceGameService;
	}

	@PostMapping("")
	@Operation(summary = "[점화] 밸런스 게임을 생성한다.")
	public ResponseEntity<?> registBalanceGame(@RequestBody @Valid CreateBalanceGameRequest createBalanceGameRequest,
		@RequestParam int userId) {
		try {
			balanceGameService.addBalanceGame(createBalanceGameRequest, userId);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("")
	@Operation(summary = "[점화] 카테고리 별, 진행 상태 별 밸런스 게임 조회 ")
	public ResponseEntity<ApiResponseDto<Map<String, Object>>> getBalanceGameByConditions(
		@RequestParam(defaultValue = "") Integer category,
		@RequestParam(defaultValue = "5") int status, @RequestParam int page,
		@RequestParam int userId) {
		User user = new User();
		user.setId(userId);
		try {
			//페이지 내 밸런스게임 리스트
			Page<BalanceGameReturnDto> list = balanceGameService.getBalanceGameByConditions(category, status, page,
				user);
			Map<String, Object> map = new HashMap<>();
			map.put("page", list);

			//유저가 투표한 내역
			List<UserVoteOpinion> userVote = balanceGameService.getUserVotelist(user);
			map.put("userVoteList", userVote);

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", map));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@PatchMapping("")
	@Operation(summary = "밸런스 게임을 삭제합니다.")
	public ResponseEntity<?> deleteBalanceGame(@RequestParam Long id, @RequestParam int userId) {

		//JWT 토큰 전(리팩토링 필요)
		User user = new User();
		user.setId(userId);

		try {
			balanceGameService.deleteBalanceGame(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	@GetMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 대한 댓글을 불러옵니다.")
	public ResponseEntity<?> getCommentListByBattleId(@RequestParam Long id, @RequestParam int page,
		@RequestParam int totalPage) {
		return new ResponseEntity<>(balanceGameService.getCommentsByBattleId(id, page, totalPage), HttpStatus.OK);
	}

	@PostMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 댓글을 작성합니다.")
	public ResponseEntity<?> addComment(@RequestBody BalanceGameCommentDto balanceGameCommentDto,
		@RequestParam("userId") int userId) {
		try {
			User user = new User();
			user.setId(userId);
			balanceGameCommentDto.setUser(user);
			balanceGameService.addComment(balanceGameCommentDto);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

}
