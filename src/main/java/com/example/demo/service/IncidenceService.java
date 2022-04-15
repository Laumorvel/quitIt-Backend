package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CommentCommunity;
import com.example.demo.model.Incidence;
import com.example.demo.model.State;
import com.example.demo.model.User;
import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.IncidenceRepo;
import com.example.demo.repository.UserRepo;

@Service
public class IncidenceService {

	@Autowired IncidenceRepo incidenceRepo;
	
	@Autowired
	CommentsCommunityRepo commentsCommutinyRepo;
	
	@Autowired UserRepo userRepo;
	

	public Incidence createIncidence(User result, Incidence datos) {
		User  user = userRepo.findById(result.getId()).orElse(null);
		
		
		Incidence incidence = new Incidence();
		
		incidence.setState(State.PENDING);
		incidence.setSubject(datos.getSubject());
		incidence.setText(datos.getText());
		incidence.setUser(user);
	    		
		
		incidenceRepo.save(incidence);
		userRepo.save(user);
	
		return incidence;
		
	}


	public Incidence editIncidence(Long i, CommentCommunity comentario) {
		if (incidenceRepo.existsById(i)) {
			
			Incidence incidence = incidenceRepo.findById(i).orElse(null);
			CommentCommunity commentCommunity = commentsCommutinyRepo.findById(comentario.getId()).orElse(null);
			
			incidence.setComment(commentCommunity);
		
			return incidenceRepo.save(incidence);
		} else {
			return null;
		}
	}


	public List<Incidence> getAllIncidences() {
		return incidenceRepo.findAll();
	}


	public CommentCommunity getCommentById(Long idC) {
		CommentCommunity comment = commentsCommutinyRepo.findById(idC).orElse(null);
		return comment;
	}


	public Incidence changeState(String estado, Incidence incidence) {
		if (incidenceRepo.existsById(incidence.getId())) {
			
			Incidence incidence2 = incidenceRepo.findById(incidence.getId()).orElse(null);
			
			if(estado.equals("RESULT")) {
				incidence2.setState(State.RESULT);
			}
			if(estado.equals("IN_PROCESS")) {
				incidence2.setState(State.IN_PROCESS);
			}
			if(estado.equals("PENDING")) {
				incidence2.setState(State.PENDING);
			}
		
			return incidenceRepo.save(incidence);
		} else {
			return null;
		}
	}


	public Incidence findById(Long idi) {
		return incidenceRepo.findById(idi).orElse(null);
	}



	
}
