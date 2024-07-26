package com.woowahanrabbits.battle_people.domain.user.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woowahanrabbits.battle_people.domain.user.domain.UserToken;
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

	public String generateAccessToken(String email) {

		return Jwts.builder()
			.setSubject(email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.compact();
	}

	public String generateRefreshToken(String email) {
		return Jwts.builder()
			.setSubject(email)
			.issuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseSignedClaims(token)
			.getBody();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	// db에 토큰과 일치하고, 토큰 기간이 유효한지 체크
	public boolean validateToken(String token, String type) {
		Long userId = userRepository.getUserIdByEmail(this.extractUsername(token));
		Optional<UserToken> userToken = userTokenRepository.findById(userId);
		if (userToken.isEmpty()) return false;
		if (isTokenExpired(token)) return false;
		UserToken getToken = userToken.get();
		if (type.equals("access")) return getToken.getAccessToken().equals(token);
		return getToken.getRefreshToken().equals(token);
	}
}
