package com.andis.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KeywordScoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeywordScoreApplication.class, args);
	}

}
