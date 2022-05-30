package com.example.demo.error;

public class PenaltyNotAddedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PenaltyNotAddedException() {
super("Penalty not added to this user");	}


}
