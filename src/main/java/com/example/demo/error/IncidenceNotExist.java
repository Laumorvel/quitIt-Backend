package com.example.demo.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class IncidenceNotExist extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2227754215995392012L;

	public IncidenceNotExist(long id) {
		super("The incidence with the id " + id + " does not exit");
	}	
}
