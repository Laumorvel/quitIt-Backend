package com.example.demo.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotExist {

	public CommentNotExist(long id) {
		//super("The comment with the id " + id + " does not exit");
	}
}
