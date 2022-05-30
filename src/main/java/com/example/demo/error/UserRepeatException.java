package com.example.demo.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserRepeatException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7246199010587911087L;

	public UserRepeatException() {
		super("The user is already added");
	}	
}
