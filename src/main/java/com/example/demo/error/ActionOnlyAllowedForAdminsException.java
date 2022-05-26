package com.example.demo.error;

public class ActionOnlyAllowedForAdminsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActionOnlyAllowedForAdminsException() {
		super("You must be an administrator of the team to proceed with this action.");
	}

}
