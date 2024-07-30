package com.woowahanrabbits.battle_people.global;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "min.people.count")
public class AppProperties {

	private int minPeopleCount;

	// Getter와 Setter
	public int getMinPeopleCount() {
		return minPeopleCount;
	}
}
