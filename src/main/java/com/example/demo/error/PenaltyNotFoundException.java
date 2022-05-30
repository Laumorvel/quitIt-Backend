package com.example.demo.error;

public class PenaltyNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PenaltyNotFoundException() {
super("Penalty not found");	}

}
