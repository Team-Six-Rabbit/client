package com.woowahanrabbits.battle_people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication
@EnableScheduling
public class BattlePeopleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlePeopleApplication.class, args);
	}

}
