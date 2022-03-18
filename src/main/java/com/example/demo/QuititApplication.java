package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@SpringBootApplication
public class QuititApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(QuititApplication.class, args);
	}

	@Bean
	CommandLineRunner initData (UserRepo repositorioUsers) {		
		User usuario1 = new User("Adela","Lira","adela@gmail.com", passwordEncoder.encode("12345"),
				"ADMIN", 3, 4.15 );
		
		return (args) -> {
			repositorioUsers.saveAll(Arrays.asList(usuario1));
			
			

			
		};
	}

	
	
	
	
}
