package com.example.demo.error;

public class AchievementNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AchievementNotFoundException() {
		super("Achievement not found");
	}

}
