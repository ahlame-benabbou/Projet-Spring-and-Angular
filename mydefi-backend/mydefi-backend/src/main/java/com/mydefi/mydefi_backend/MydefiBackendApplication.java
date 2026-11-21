package com.mydefi.mydefi_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MydefiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydefiBackendApplication.class, args);
	}

}
