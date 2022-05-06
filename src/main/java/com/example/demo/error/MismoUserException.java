package com.example.demo.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MismoUserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4189464023232696776L;

	public MismoUserException() {
		super("You cannot add your user as a friend");
	}	
}
