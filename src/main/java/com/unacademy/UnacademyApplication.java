package com.unacademy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class UnacademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnacademyApplication.class, args);


	}





}

