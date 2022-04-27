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
	

	/**
	 * Crea una incidencia.
	 * Añade el asunto y el texto que indique el usuario.
	 * Tambien se guarda el usuario que ha realizado la incidencia.
	 * El estado de una incidencia al crearse siempre sera "PENDING"	
	 * @param result
	 * @param datos
	 * @return
	 */
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


	/**
	 * Añade a la incidencia anterior el comentario al que hace referencia
	 * @param i
	 * @param comentario
	 * @return
	 */
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


	/**
	 * Muestra una lista de incidencias
	 * @return
	 */
	public List<Incidence> getAllIncidences() {
		return incidenceRepo.findAll();
	}


	/**
	 * Busca un comentario por id y lo devuelve
	 * @param idC
	 * @return
	 */
	public CommentCommunity getCommentById(Long idC) {
		CommentCommunity comment = commentsCommutinyRepo.findById(idC).orElse(null);
		return comment;
	}


	/**
	 * Canmbia el estado de una incidencia que identificamos por su id
	 * @param estado
	 * @param incidence
	 * @return
	 */
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

	/**
	 * Devuelve una incidencia por id
	 * @param idi
	 * @return
	 */
	public Incidence findById(Long idi) {
		return incidenceRepo.findById(idi).orElse(null);
	}



	
}
