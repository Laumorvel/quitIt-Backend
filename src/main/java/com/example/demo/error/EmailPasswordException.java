package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailPasswordException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7238076647889171067L;

	public EmailPasswordException() {
		super("The email and password is incorrect");
	}	

}
