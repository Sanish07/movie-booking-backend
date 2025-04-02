package com.sanish.movie_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfiguration.class)
@SpringBootTest
class MovieServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
