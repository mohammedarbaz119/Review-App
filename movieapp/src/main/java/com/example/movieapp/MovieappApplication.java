package com.example.movieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class MovieappApplication {


	@GetMapping(value = "/")
	@Secured("USER")
	public String getvlaue(){
		return "arbaz";
	}
	public static void main(String[] args) {
		SpringApplication.run(MovieappApplication.class, args);
	}

}
