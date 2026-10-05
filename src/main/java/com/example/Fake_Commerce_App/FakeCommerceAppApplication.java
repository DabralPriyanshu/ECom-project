package com.example.Fake_Commerce_App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FakeCommerceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakeCommerceAppApplication.class, args);
	}

}
