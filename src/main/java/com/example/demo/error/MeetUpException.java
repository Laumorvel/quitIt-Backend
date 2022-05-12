package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeetUpException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -301461885226707686L;

	public MeetUpException(Long id) {
		super("The meet up with the " + id + " not exist");
	}

}
