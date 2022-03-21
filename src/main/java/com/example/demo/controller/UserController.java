package com.example.demo.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.EmailNotExistException;
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
    		throw new UserNotFoundException(result.getId());
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	}
     	
	}
	
	
	 @GetMapping("/user/email/{email}")
	    public User compruebaEmail( @PathVariable String email) { 	
	    	if(userService.findByEmail(email)!=null) {
	    		return userService.findByEmail(email);   	
	    	}
	    	else {
	    		throw new EmailNotExistException(email);
	    	}
	    	
	    }

	
	
	@PostMapping("/sendMail")
    public void sendEmail(@RequestBody Message datos) throws MessagingException {
    	datos.setTo("aalira.96@gmail.com");
    	
		smtpMailSender.send(datos.getTo(), datos.getSubject(), datos.getText());
	} 
}
