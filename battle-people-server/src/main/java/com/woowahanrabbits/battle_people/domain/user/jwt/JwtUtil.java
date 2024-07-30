package com.woowahanrabbits.battle_people.domain.user.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;
import com.woowahanrabbits.battle_people.domain.user.handler.JwtAuthenticationException;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;
import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.accessToken.expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refreshToken.expiration}")
	private long refreshTokenExpiration;

	public String generateAccessToken(long userId, String email, String role) {

		return Jwts.builder()
			.subject(email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.claim("userId", userId)
			.claim("role", role)
			.compact();
	}

	public String generateRefreshToken(long userId, String email, String role) {
		return Jwts.builder()
			.subject(email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
			.claim("userId", userId)
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseSignedClaims(token).getBody();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public Long extractUserId(String token) {
		return extractClaims(token).get("userId", Long.class);
	}

	public String extractUserRole(String token) {
		return extractClaims(token).get("role", String.class);
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	// DB에 토큰과 일치하고, 토큰 기간이 유효한지 체크
	public boolean validateToken(String token, String type) {
		try {
			UserToken userToken = userTokenRepository.findByAccessToken(token); // 수정된 부분

			if (userToken == null) { // 수정된 부분
				throw new JwtAuthenticationException("User token not found in DB for accessToken: " + token);
			}

			if (isTokenExpired(token)) {
				throw new JwtAuthenticationException("JWT token is expired");
			}

			if ("access".equals(type) && userToken.getAccessToken().equals(token)) {
				return true;
			} else if ("refresh".equals(type) && userToken.getRefreshToken().equals(token)) {
				return true;
			} else {
				throw new JwtAuthenticationException("Token does not match the type: " + type);
			}

		} catch (JwtAuthenticationException e) {
			throw e;
		} catch (Exception e) {
			throw new JwtAuthenticationException("Token validation error: " + e.getMessage());
		}
	}
	// // db에 토큰과 일치하고, 토큰 기간이 유효한지 체크
	// public boolean validateToken(String token, String type) {
	// 	Long userId = userRepository.getUserIdByEmail(this.extractUsername(token));
	// 	Optional<UserToken> userToken = userTokenRepository.findById(userId);
	// 	if (userToken.isEmpty())
	// 		return false;
	// 	if (isTokenExpired(token))
	// 		return false;
	// 	UserToken getToken = userToken.get();
	// 	if (type.equals("access"))
	// 		return getToken.getAccessToken().equals(token);
	// 	return getToken.getRefreshToken().equals(token);
	// }
}
