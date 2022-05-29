package com.example.demo.error;

public class MemberNotAddedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotAddedException() {
		super("Member not added to the group");
	}
}
