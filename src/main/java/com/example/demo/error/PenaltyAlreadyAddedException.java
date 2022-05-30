package com.example.demo.error;

public class PenaltyAlreadyAddedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PenaltyAlreadyAddedException() {
super("Penalty already added to this user");	}


}
