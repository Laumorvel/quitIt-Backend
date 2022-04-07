package com.example.demo.error;

public class TypeMismatchException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TypeMismatchException() {
		super ("This type do not correspond to the expected type");
	}

}
