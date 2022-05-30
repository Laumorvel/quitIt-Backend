package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.UserNotAttendanceException;
import com.example.demo.error.UserRepeatException;
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
			MeetUp meet = meetUpRepo.findById(id).orElse(null);
			for (int i = 0; i < meet.getAssistantsList().size(); i++) {
				if(meet.getAssistantsList().get(i).getUsername().equals(result.getUsername())){
					throw new UserRepeatException();
				}
			}
			
			meet.addAttendace(result);
			meetUpRepo.save(meet);

			return meet;
	}

	/**
	 * Eliminar asistencia a un meet up
	 * @param result
	 * @param id
	 * @return
	 */
	public MeetUp deletetAttendace(User result, Long id) {
		Boolean noExiste = false;
		MeetUp meet = meetUpRepo.findById(id).orElse(null);
		for (int i = 0; i < meet.getAssistantsList().size(); i++) {
			if(meet.getAssistantsList().get(i).getUsername().equals(result.getUsername())){
				meet.deleteAttendace(result);
				meetUpRepo.save(meet);
				noExiste=true;
			}
		}
		if(!noExiste) {
			throw new UserNotAttendanceException();
		}

		
		return meet;
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
