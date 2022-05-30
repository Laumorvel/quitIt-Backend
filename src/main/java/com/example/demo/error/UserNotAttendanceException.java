package com.example.demo.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotAttendanceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2421138068636478839L;

	public UserNotAttendanceException() {
		super("The user don't attendance the meet up");
	}	
}
	

