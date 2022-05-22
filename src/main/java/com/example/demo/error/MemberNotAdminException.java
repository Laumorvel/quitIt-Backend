package com.example.demo.error;

public class MemberNotAdminException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotAdminException() {
		super("The member of the group must be admin to modify the role of another member");
	}
}
