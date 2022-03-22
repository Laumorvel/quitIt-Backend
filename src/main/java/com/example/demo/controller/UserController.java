package com.example.demo.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.SmtpMailSender;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	 @Autowired private UserRepo userRepo;
	 
	 @Autowired private UserService userService;
	 
	 @Autowired private SmtpMailSender smtpMailSender;
	    
	  
	
	@GetMapping("/user")
    public ResponseEntity<User> getUser() {
    	
    	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User result =  userRepo.findByEmail(email);
        
    	if(result==null) {
    		throw new UserNotFoundException();
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	}
     	
	}
	
	
	 @GetMapping("/email")
	 public User checkEmailUsers(@RequestParam(required = false) String email, @RequestParam(required = false) String username) {
			if (username == null) {
				return userService.getUserEmail(email);
			} else {
				return userService.getUsername(username);
			}
		}



	
	
	@PostMapping("/mail")
    public void sendEmail(@RequestBody Message datos) throws MessagingException {
    	datos.setToUser("aalira.96@gmail.com");
    	
		smtpMailSender.send(datos.getToUser(), datos.getSubject(), datos.getText(), datos.getFromUser());
	} 
}
