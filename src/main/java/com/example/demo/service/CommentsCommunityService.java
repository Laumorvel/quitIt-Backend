package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CommentCommunity;
import com.example.demo.model.User;
import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.UserRepo;

@Service
public class CommentsCommunityService {

	@Autowired
	CommentsCommunityRepo commentsCommutinyRepo;

	@Autowired
	UserRepo userRepo;

	public List<CommentCommunity> getComments() {
		return commentsCommutinyRepo.findAll();
	}

	public CommentCommunity addCommentCommunity(User result, CommentCommunity datos) {
		User user = userRepo.findById(result.getId()).orElse(null);// ya tienes al usuario

		CommentCommunity comment = new CommentCommunity();

		comment.setDate(LocalDate.now());
		comment.setText(datos.getText());
		comment.setUser(user);

		commentsCommutinyRepo.save(comment);
		userRepo.save(user);

		return comment;

	}

	public CommentCommunity delete(Long idC) {
		if (commentsCommutinyRepo.existsById(idC)) {
			
			CommentCommunity c = commentsCommutinyRepo.findById(idC).get();
			
			commentsCommutinyRepo.deleteById(idC);
			
			return c;
		} else {
			return null;
		}
	}

}
