package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5720418567336067971L;

	public UserExistException() {
		super("This email already exists");
	}	
	
}
