package com.example.demo.error;

public class RepeatedMembersFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RepeatedMembersFoundException() {
		super("Repeated group members have been found within the group");
	}

}
