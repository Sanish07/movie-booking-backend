package com.sanish.movie_service;

import org.springframework.boot.SpringApplication;

public class TestMovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MovieServiceApplication::main).with(ContainersConfiguration.class).run(args);
	}

}
