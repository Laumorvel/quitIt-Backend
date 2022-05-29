package com.example.demo.error;

public class NoChangeOfRoleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoChangeOfRoleException() {
		super("This change does not modify anything");
	}


}
