package com.woowahanrabbits.battle_people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class BattlePeopleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlePeopleApplication.class, args);
	}

}
