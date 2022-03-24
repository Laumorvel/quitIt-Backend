package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CommentsCommunity;
import com.example.demo.model.Incidence;
import com.example.demo.model.State;
import com.example.demo.model.User;
import com.example.demo.repository.IncidenceRepo;
import com.example.demo.repository.UserRepo;

@Service
public class IncidenceService {

	@Autowired IncidenceRepo incidenceRepo;
	
	@Autowired UserRepo userRepo;
	

	public Incidence createIncidence(User result, Incidence datos) {
		User  user = userRepo.findById(result.getId()).orElse(null);
		
		
		Incidence incidence = new Incidence();
		
		incidence.setState(State.PENDING);
		incidence.setSubject(datos.getSubject());
		incidence.setText(datos.getText());
		incidence.setUser(user);
	    		
		//user.getListaCitas().add(cita);
		incidenceRepo.save(incidence);
		userRepo.save(user);
	
		return incidence;
		
	}
	
	
}
