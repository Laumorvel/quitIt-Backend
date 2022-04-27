package com.example.demo.error;

public class NonExistentAchievementException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentAchievementException() {
		super("Non-existent achievement");
	}

}
