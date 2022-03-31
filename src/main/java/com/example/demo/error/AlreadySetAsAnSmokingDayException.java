package com.example.demo.error;

public class AlreadySetAsAnSmokingDayException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadySetAsAnSmokingDayException() {
		super("You already set this date as a day when you smoked");
	}
}
