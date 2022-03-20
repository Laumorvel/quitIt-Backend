package com.example.demo.controller;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.EmailPasswordException;
import com.example.demo.error.PasswordException;
import com.example.demo.model.LoginCredentials;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JWTUtil;


@RestController
public class AuthController {


    @Autowired private UserRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    
    
    
    

    @PostMapping("/auth/register")
    public Map<String, Object> registerHandler(@RequestBody User user, User body) throws Exception{
    	
    	if (userRepo.findByEmail(user.getEmail())==null) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setRol("user");
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRol());
        System.out.println("HOLA");
        return Collections.singletonMap("access_token", token);
    	}
	    else {
			throw new Exception();
		}
    }


    @PostMapping("/auth/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String rol = userRepo.findByEmail(body.getEmail()).getRol();
            String token = jwtUtil.generateToken(body.getEmail(), rol);

            return Collections.singletonMap("access_token", token);
        }
        catch (AuthenticationException authExc){
        	if(this.userRepo.findByEmail(body.getEmail()) != null) {
        		throw new PasswordException();
        	}
        	else {
        		throw new EmailPasswordException();
        	}
            	
        } 
    }
    
    
    
    
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ApiError> passwordError(PasswordException ex) throws Exception {
    	ApiError e = new ApiError();
    	e.setEstado(HttpStatus.BAD_REQUEST);
    	e.setMensaje(ex.getMessage());
    	e.setFecha(LocalDateTime.now());
    	
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
	} 
    

    @ExceptionHandler(EmailPasswordException.class)
    public ResponseEntity<ApiError> emailPasswordError(EmailPasswordException ex) throws Exception {
    	ApiError e = new ApiError();
    	e.setEstado(HttpStatus.BAD_REQUEST);
    	e.setMensaje(ex.getMessage());
    	e.setFecha(LocalDateTime.now());
    	
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
	} 

    
}