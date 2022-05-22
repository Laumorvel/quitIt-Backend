package com.example.demo.error;

public class ModifyingSelfGroupMemberException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModifyingSelfGroupMemberException() {
		super("You cannot modify your own group member");
	}


}
