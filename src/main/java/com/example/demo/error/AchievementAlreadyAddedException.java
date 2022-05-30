package com.example.demo.error;

public class AchievementAlreadyAddedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AchievementAlreadyAddedException() {
super("Achievement already added to this user");	}

}
