package com.example.demo.error;

public class AchievementNotAddedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AchievementNotAddedException() {
		super("Achievement not added to this user");	
		}


}
