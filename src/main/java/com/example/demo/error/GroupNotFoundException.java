package com.example.demo.error;

public class GroupNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupNotFoundException() {
		super("The group does not exist");
	}

}
