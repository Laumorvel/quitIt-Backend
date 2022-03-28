package com.example.demo.error;

public class AlreadyExistingFileException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistingFileException() {
		super("Already existing file");
	}

}
