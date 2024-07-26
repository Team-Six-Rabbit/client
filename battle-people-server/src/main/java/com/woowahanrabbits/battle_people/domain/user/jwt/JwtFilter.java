package com.woowahanrabbits.battle_people.domain.user.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.woowahanrabbits.battle_people.domain.user.service.PrincipalDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final PrincipalDetailsService principalDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (request.getCookies() == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String access = null;
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("access")) {
				access = cookie.getValue();
			}
			if (cookie.getName().equals("refresh")) {
				refresh = cookie.getValue();
			}
		}

		if (access != null) {
			if (jwtUtil.validateToken(access, "access")) {
				setAuthentication(jwtUtil.extractUsername(access));
			}
			else if(refresh != null) {
				if (jwtUtil.validateToken(refresh, "refresh")) {
					String username = jwtUtil.extractUsername(refresh);
					String newAccess = jwtUtil.generateAccessToken(username);
					response.addCookie(createCookie("access", newAccess, "/"));
					setAuthentication(username);
					return;
				}
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		}

		filterChain.doFilter(request, response);
	}

	private Cookie createCookie(String name, String value, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath(path);
		cookie.setMaxAge(60 * 60);  // 1시간
		return cookie;
	}

	public void setAuthentication(String email) {
		Authentication authentication = createAuthentication(email);
		// security가 만들어주는 securityContextHolder 그 안에 authentication을 넣어줍니다.
		// security가 securitycontextholder에서 인증 객체를 확인하는데
		// jwtAuthfilter에서 authentication을 넣어주면 UsernamePasswordAuthenticationFilter 내부에서 인증이 된 것을 확인하고 추가적인 작업을 진행하지 않습니다.
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public Authentication createAuthentication(String email) {
		UserDetails userDetails = principalDetailsService.loadUserByUsername(email);
		// spring security 내에서 가지고 있는 객체입니다. (UsernamePasswordAuthenticationToken)
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}

