package com.woowahanrabbits.battle_people.domain.user.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.accessToken.expiration}")
	private long accessTokenExpiration;

	@Value("${jwt.refreshToken.expiration}")
	private long refreshTokenExpiration;

	public String generateAccessToken(String email) {

		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
			.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
			.compact();
	}

	public String generateRefreshToken(String email) {
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date())
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

	public boolean validateToken(String token, String email) {
		return (email.equals(extractUsername(token)) && !isTokenExpired(token));
	}
}
