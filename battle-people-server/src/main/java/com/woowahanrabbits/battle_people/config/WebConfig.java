package com.woowahanrabbits.battle_people.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
					.allowedOrigins("http://localhost:3000", "http://70.12.247.159:3000") // 허용할 도메인
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true)
					.maxAge(3600);
			}
		};
	}
}
