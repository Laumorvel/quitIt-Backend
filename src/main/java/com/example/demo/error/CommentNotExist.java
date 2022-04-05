package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotExist extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6662296370007028004L;

	public CommentNotExist(Long id) {
		super("The comment with the " + id + " not exist");
	}

}
