package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@RestController
public class UserController {
	
	 @Autowired private UserRepo userRepo;
	    
	  
	
	@GetMapping("/user")
    public ResponseEntity<User> getUser() {
    	
    	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User result =  userRepo.findByEmail(email);
        
    	if(result==null) {
    		throw new UserNotFoundException(result.getId());
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	}
    	
    	
	}

}
