package com.woowahanrabbits.battle_people.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("SSAFIT")
			.description("<h3>SSAFIT에서 사용되는 RESTAPI에 대한 문서를 제공한다.</h3>")
			.version("1.0.0");
	}
}

