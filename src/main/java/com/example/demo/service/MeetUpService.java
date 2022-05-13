package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MeetUp;
import com.example.demo.model.User;
import com.example.demo.repository.MeetUpRepo;
import com.example.demo.repository.UserRepo;

@Service
public class MeetUpService {
	
	@Autowired MeetUpRepo meetUpRepo;
	
	@Autowired UserRepo userRepo;

	/**
	 * Devuelve una lista con todos los meet ups
	 * @return
	 */
	public List<MeetUp> getAllMeetUps() {
		return meetUpRepo.findAllMeetUps();
	}

	/**
	 * Añadir asistencia a un meet up
	 * @param result
	 * @param id
	 * @return
	 */
	public MeetUp addAttendace(User result, Long id) {
		if (userRepo.existsById(result.getId())) {
			MeetUp meet = meetUpRepo.findById(id).orElse(null);
			
			meet.addAttendace(result);
			
			meetUpRepo.save(meet);

			return meet;
		} else {
			return null;
		}
	}

	/**
	 * Eliminar asistencia a un meet up
	 * @param result
	 * @param id
	 * @return
	 */
	public MeetUp deletetAttendace(User result, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Crear un meet up
	 * @param meet
	 * @return
	 */
	public MeetUp createMeetUp(MeetUp meet) {
		MeetUp meetup = new MeetUp();
		meetup.setDate(meet.getDate());
		meetup.setDescription(meet.getDescription());
		meetup.setTitle(meet.getTitle());
		meetup.setType(meet.getType());
		meetup.setPlace(meet.getPlace());
		
		meetUpRepo.save(meetup);
		
		return meetup;
	}

}
