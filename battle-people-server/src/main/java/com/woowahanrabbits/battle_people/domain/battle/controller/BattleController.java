// package com.woowahanrabbits.battle_people.domain.battle.controller;
//
// import java.util.List;
//
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleApplyDto;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleInviteRequest;
// import com.woowahanrabbits.battle_people.domain.battle.dto.BattleRespondRequest;
// import com.woowahanrabbits.battle_people.domain.battle.service.BattleService;
// import com.woowahanrabbits.battle_people.domain.user.domain.User;
// import com.woowahanrabbits.battle_people.domain.user.dto.PrincipalDetails;
// import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
// import com.woowahanrabbits.battle_people.domain.vote.infrastructure.VoteInfoRepository;
// import com.woowahanrabbits.battle_people.domain.vote.service.VoteService;
//
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;
//
// @RestController
// @RequestMapping("/battle")
// @Tag(name = "BattleController", description = "배틀 컨트롤러")
// public class BattleController {
//
// 	private final BattleService battleService;
// 	private final VoteService voteService;
// 	private final UserRepository userRepository;
// 	private final VoteInfoRepository voteInfoRepository;
//
// 	public BattleController(BattleService battleService, VoteService voteService, UserRepository userRepository,
// 		VoteInfoRepository voteInfoRepository) {
// 		this.battleService = battleService;
// 		this.voteService = voteService;
// 		this.userRepository = userRepository;
// 		this.voteInfoRepository = voteInfoRepository;
// 	}
//
// 	//배틀 등록
// 	@PostMapping("/invite")
// 	@Operation(summary = "[점화] 배틀을 요청한다.")
// 	public ResponseEntity<?> registBattle(@RequestBody @Valid BattleInviteRequest battleInviteRequest,
// 		Authentication authentication) {
//
// 		try {
// 			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
// 			User user = principalDetails.getUser();
//
// 			battleService.registBattle(battleInviteRequest, user);
// 			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
// 		} catch (Exception e) {
// 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 				.body(new ApiResponseDto<>("error", "", null));
// 		}
// 	}
//
// 	//요청한, 요청받은 배틀 조회
// 	@GetMapping("")
// 	@Operation(summary = "요청한, 요청받는 배틀을 조회한다.")
// 	public ResponseEntity<?> getRequestBattleList(@RequestParam(defaultValue = "received") String type,
// 		Authentication authentication,
// 		@RequestParam int page) {
// 		try {
// 			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
// 			User user = principalDetails.getUser();
// 			return ResponseEntity.status(HttpStatus.OK)
// 				.body(new ApiResponseDto<>("success", "", battleService.getRequestBattleList(type, user, page)));
//
// 		} catch (Exception e) {
// 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 				.body(new ApiResponseDto<>("error", "", null));
// 		}
// 	}
//
// 	@PatchMapping("/accept-or-decline")
// 	@Operation(summary = "[불씨] 배틀을 수락 또는 거절한다.")
// 	public ResponseEntity<?> acceptOrDeclineBattle(@RequestBody BattleRespondRequest battleRespondRequest,
// 		Authentication authentication) {
//
// 		try {
// 			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
// 			User user = principalDetails.getUser();
// 			battleService.acceptOrDeclineBattle(battleRespondRequest, user);
// 			return ResponseEntity.status(HttpStatus.OK)
// 				.body(new ApiResponseDto<>("success", "", null));
// 		} catch (Exception e) {
// 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 				.body(new ApiResponseDto<>("error", "", null));
//
// 		}
// 	}
//
// 	@GetMapping("/apply-list")
// 	@Operation(summary = "[불씨] 모집중인 배틀을 조회한다.")
// 	public ResponseEntity<?> getAwaitingBattleList(@RequestParam(defaultValue = "") Integer category, int page,
// 		Authentication authentication) {
// 		try {
// 			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
// 			User user = principalDetails.getUser();
// 			List<?> list = battleService.getAwaitingBattleList(category, page, user);
// 			return ResponseEntity.status(HttpStatus.OK)
// 				.body(new ApiResponseDto<>("success", "", list));
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 				.body(new ApiResponseDto<>("error", "", null));
// 		}
// 	}
//
// 	@PostMapping("/apply")
// 	@Operation(summary = "모집중인 특정 배틀에 참여 신청한다.")
// 	public ResponseEntity<?> applyBattle(@RequestBody @Valid BattleApplyDto battleApplyDto,
// 		Authentication authentication) {
// 		try {
// 			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
// 			User user = principalDetails.getUser();
// 			battleService.applyBattle(battleApplyDto, user);
// 			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>("success", "", null));
// 		} catch (Exception e) {
// 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// 				.body(new ApiResponseDto<>("error", "", null));
// 		}
//
// 	}
// }
