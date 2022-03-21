package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageWronglyFormedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3187778203175959594L;

	public MessageWronglyFormedException() {
		super("The message must contain an email, a name and a message.");
	}

}
