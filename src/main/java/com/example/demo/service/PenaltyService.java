package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.PenaltyAlreadyAddedException;
import com.example.demo.error.PenaltyNotAddedException;
import com.example.demo.error.PenaltyNotFoundException;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.PenaltyRepo;
import com.example.demo.repository.UserRepo;

@Service
public class PenaltyService {
	
	@Autowired PenaltyRepo penaltyRepo;
	@Autowired
	UserRepo userRepo;

	/**
	 * Devuelve una lista con todas las penalizaciones existentes
	 * @return
	 */
	public List<Penalty> getAllPenalty() {
		return penaltyRepo.findAllPenalty();
	}
	
	/**
	 * Consigue una penalización
	 * @param id
	 * @return penalización
	 */
	public Penalty getPenalty(Long id) {
		if(penaltyRepo.getById(id) != null) {
			return penaltyRepo.getById(id);
		}else {
			throw new PenaltyNotFoundException();
		}
	}
	
	/**
	 * Elimina una penalización del usuario
	 * @param user
	 * @param id
	 */
	public void deletePenaltyFromUser(User user, Long id) {
		Penalty penalty;
		if(penaltyRepo.getById(id) != null) {
			penalty = penaltyRepo.getById(id);
		}else {
			throw new PenaltyNotFoundException();
		}
		Boolean found = false;
		for (Penalty p : user.getPenalties()) {
			if (p.equals(penalty)) {
				found = true;
			}
		}
		if(!found) {
			throw new PenaltyNotAddedException();
		}
		user.getPenalties().remove(penalty);
		userRepo.save(user);
	}
	
	/**
	 * Elimina una penalización
	 * @param id
	 */
	public void deletePenalty(Long id) {
		Penalty penalty;
		if(penaltyRepo.getById(id) != null) {
			penalty = penaltyRepo.getById(id);
		}else {
			throw new PenaltyNotFoundException();
		}
		penaltyRepo.delete(penalty);
	}
	
	/**
	 * Añade una nueva penalización
	 * @param penalty
	 * @return
	 */
	public Penalty addPenalty(Penalty penalty) {
		for (Penalty p : penaltyRepo.findAll()) {
			if(p.equals(penalty)) {
				throw new PenaltyAlreadyAddedException();
			}
		}
		return penaltyRepo.save(penalty);
	}
	
	

}
