package com.example.demo.error;

public class MemberNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException() {
		super("The member of the group does not exist");
	}


}
