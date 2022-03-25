package com.example.demo.service;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CommentsCommunity;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.UserRepo;

@Service
public class CommentsCommunityService {
	@Autowired CommentsCommunityRepo commentsCommutinyRepo;
	
	@Autowired UserRepo userRepo;
	
	
	
	public List<CommentsCommunity> getComments() {
		return commentsCommutinyRepo.findAllCommentsComunity();
	}



	public CommentsCommunity addCommentCommunity(User result, CommentsCommunity datos) {
		User  user = userRepo.findById(result.getId()).orElse(null);
		
    		
			CommentsCommunity comment = new CommentsCommunity();
    		
    		comment.setDate(LocalDate.now());
    		comment.setText(datos.getText());
    		comment.setUser(user);
    	    		
    		//user.getListaCitas().add(cita);
    		commentsCommutinyRepo.save(comment);
    		userRepo.save(user);
    	
    		return comment;
    		
	}
	
}
