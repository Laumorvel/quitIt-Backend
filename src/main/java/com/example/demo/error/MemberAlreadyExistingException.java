package com.example.demo.error;

public class MemberAlreadyExistingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberAlreadyExistingException() {
		super("Member already added to the group");
	}

}
