package com.woowahanrabbits.battle_people.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.woowahanrabbits.battle_people.domain.user.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final AuthenticationConfiguration authenticationConfiguration;
	private final UserRepository userRepository;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.formLogin(form -> form.disable());
		http.httpBasic(basic -> basic.disable());

		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/user/join", "/auth/login", "/auth/logout", "/auth/refresh", "/", "/test")
			.permitAll()
			.anyRequest()
			.authenticated());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
