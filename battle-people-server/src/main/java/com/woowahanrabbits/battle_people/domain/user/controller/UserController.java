package com.woowahanrabbits.battle_people.domain.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowahanrabbits.battle_people.domain.api.dto.ApiResponseDto;
import com.woowahanrabbits.battle_people.domain.user.domain.User;
import com.woowahanrabbits.battle_people.domain.user.dto.BasicUserDto;
import com.woowahanrabbits.battle_people.domain.user.dto.InterestRequest;
import com.woowahanrabbits.battle_people.domain.user.dto.JoinRequest;
import com.woowahanrabbits.battle_people.domain.user.resolver.LoginUser;
import com.woowahanrabbits.battle_people.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<ApiResponseDto<User>> join(@RequestBody JoinRequest request) {
		User user = userService.join(request);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User joined", user));
	}

	@GetMapping("/profile")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseDto<BasicUserDto>> getLoginUserProfile(@LoginUser User loginUser) {
		User user = userService.getUserProfile(loginUser.getId());
		BasicUserDto userDto = new BasicUserDto(user);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", userDto));
	}

	@GetMapping("/profile/{userId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseDto<BasicUserDto>> getUserProfile(@PathVariable(value = "userId") Long userId) {
		User user = userService.getUserProfile(userId);
		BasicUserDto userDto = new BasicUserDto(user);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User profile", userDto));
	}

	@GetMapping("/interest")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseDto<Map<String, List<Integer>>>> getUserInterest(@LoginUser User user) {
		List<Integer> list = userService.getInterest(user.getId());
		Map<String, List<Integer>> response = new HashMap<>();
		response.put("category", list);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "User interest", response));
	}

	@PostMapping("/interest")
	public ResponseEntity<ApiResponseDto<Void>> setUserInterest(@LoginUser User user,
		@RequestBody InterestRequest request) {
		userService.setInterest(user.getId(), request);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "Create User Category", null));
	}

	@GetMapping("/check/nickname")
	public ResponseEntity<ApiResponseDto<Boolean>> checkNickname(@RequestParam String nickname) {
		boolean isAvailable = userService.isNicknameAvailable(nickname);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "닉네임 확인", isAvailable));
	}

	@GetMapping("/check/email")
	public ResponseEntity<ApiResponseDto<Boolean>> checkEmail(@RequestParam String email) {
		boolean isAvailable = userService.isEmailAvailable(email);
		return ResponseEntity.ok(new ApiResponseDto<>("success", "닉네임 확인", isAvailable));
	}

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<BasicUserDto>>> getUserByNickname(
		@LoginUser User loginuser,
		@RequestParam("nickname") String nickname) {

		List<User> userList = userService.findByNickname(nickname);
		List<BasicUserDto> userDtoList = new ArrayList<>();
		for (User user : userList) {
			if (user.getNickname().equals(loginuser.getNickname())) {
				continue;
			}
			userDtoList.add(new BasicUserDto(user));
		}
		return ResponseEntity.ok(new ApiResponseDto<>("success", "user", userDtoList));
	}

	@Value("${storage.location}")
	private String uploadDir;

	@PostMapping("/upload")
	public ResponseEntity<ApiResponseDto<String>> handleFileUpload(@LoginUser User user,
		@RequestPart("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(new ApiResponseDto<>("fail", "파일이 없습니다.", null));
		}

		try {
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			File dest = new File(uploadDir + File.separator + fileName);
			file.transferTo(dest);

			System.out.println("File saved to: " + dest.getAbsolutePath()); // 디버깅 로그
			userService.updateUserImgUrl(user.getId(), fileName);

			return ResponseEntity.ok(new ApiResponseDto<>("success", "파일 업로드 성공", fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponseDto<>("fail", "INTERNAL_SERVER_ERROR", null));
		}
	}

	// @GetMapping("/images/{fileName:.+}")
	// public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
	// 	System.out.println(fileName);
	// 	try {
	// 		Path filePath = Paths.get("file:" + uploadDir).resolve(fileName).normalize();
	// 		Resource resource = new UrlResource(filePath.toUri());
	//
	// 		if (!resource.exists()) {
	// 			return ResponseEntity.notFound().build();
	// 		}
	//
	// 		System.out.println("Serving file from: " + filePath.toString()); // 디버깅 로그
	//
	// 		String contentType = Files.probeContentType(filePath);
	// 		if (contentType == null) {
	// 			contentType = "application/octet-stream";
	// 		}
	//
	// 		return ResponseEntity.ok()
	// 			.contentType(MediaType.parseMediaType(contentType))
	// 			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	// 			.body(resource);
	// 	} catch (MalformedURLException e) {
	// 		e.printStackTrace();
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// 	} catch (IOException e) {
	// 		e.printStackTrace();
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// 	}
	// }
}
