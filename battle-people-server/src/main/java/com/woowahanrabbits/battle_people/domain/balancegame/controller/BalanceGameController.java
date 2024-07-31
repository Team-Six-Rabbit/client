package com.woowahanrabbits.battle_people.domain.balancegame.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.AddBalanceGameCommentRequest;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.BalanceGameResponse;
import com.woowahanrabbits.battle_people.domain.balancegame.dto.CreateBalanceGameRequest;
import com.woowahanrabbits.battle_people.domain.balancegame.service.BalanceGameService;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.PrincipalDetails;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/balance-game")
@Tag(name = "BalanceGameController", description = "밸런스게임 컨트롤러")
public class BalanceGameController {

	private final BalanceGameService balanceGameService;
	private final UserRepository userRepository;

	public BalanceGameController(BalanceGameService balanceGameService, UserRepository userRepository) {
		this.balanceGameService = balanceGameService;
		this.userRepository = userRepository;
	}

	@PostMapping("")
	@Operation(summary = "[점화] 밸런스 게임을 생성한다.")
	public ResponseEntity<?> registBalanceGame(@RequestBody @Valid CreateBalanceGameRequest createBalanceGameRequest,
		Authentication authentication) {
		try {
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			User user = principalDetails.getUser();
			balanceGameService.addBalanceGame(createBalanceGameRequest, user.getId());
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("")
	@Operation(summary = "[점화] 카테고리 별, 진행 상태 별 밸런스 게임 조회 ")
	public ResponseEntity<ApiResponseDto<?>> getBalanceGameByConditions(
		@RequestParam(defaultValue = "") Integer category,
		@RequestParam(defaultValue = "5") int status, @RequestParam int page,
		Authentication authentication) {

		try {

			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			User user = principalDetails.getUser();

			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "",
				balanceGameService.getBalanceGameByConditions(category, status, page, user)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Id 값으로 밸런스 게임 조회")
	public ResponseEntity<ApiResponseDto<?>> getBalanceGameById(@PathVariable Long id, Authentication authentication) {
		try {
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			User user = principalDetails.getUser();
			BalanceGameResponse balanceGameResponse = balanceGameService.getBalanceGameById(id, user);
			return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDto<>("success", "", balanceGameResponse));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@PatchMapping("")
	@Operation(summary = "밸런스 게임을 삭제합니다.")
	public ResponseEntity<?> deleteBalanceGame(@RequestParam Long id, Authentication authentication) {

		try {
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			User user = principalDetails.getUser();
			balanceGameService.deleteBalanceGame(id, user);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

	@GetMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 대한 댓글을 불러옵니다.")
	public ResponseEntity<?> getCommentListByBattleId(@RequestParam @Valid Long id) {
		try {
			List<?> list = balanceGameService.getCommentsByBattleId(id);
			if (list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>("success", "", null));
			}
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", list));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}
	}

	@PostMapping("/comment")
	@Operation(summary = "특정 밸런스 게임에 댓글을 작성합니다.")
	public ResponseEntity<?> addComment(@RequestBody @Valid AddBalanceGameCommentRequest addBalanceGameCommentRequest,
		Authentication authentication) {
		try {
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			User user = principalDetails.getUser();
			balanceGameService.addComment(addBalanceGameCommentRequest, user);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "internal server error", null));
		}

	}

}
