package com.app.guess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.app.guess.mapper")
@SpringBootApplication
public class GuessApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuessApplication.class, args);
	}

}
