package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ScheduledMessage;
import com.example.demo.model.User;
import com.example.demo.repository.ScheduledMessageRepository;
import com.example.demo.repository.UserRepo;

@Service
public class ScheduledMessageService {

	@Autowired
	ScheduledMessageRepository scheduleMessageRepository;
	
	@Autowired
	UserRepo userRepo;

	/**
	 * Devuelve el siguiente mensaje de la lista. Esto se marca por el mensaje que
	 * tenga la propiedad sent a true.
	 * El mensaje a enviar se seteará a true mientras que el anterior enviado pasará a false
	 * 
	 * @return mensaje a enviar seteado a true
	 */
	public ScheduledMessage getScheduledMessageToSend() {
		setMessagePropertyToTrue();
		Long lastMessageSentId = scheduleMessageRepository.getLastMessageSentId() < 15
				? scheduleMessageRepository.getLastMessageSentId()
				: 0;
		scheduleMessageRepository.getLastMessageSent().setSent(false);
		scheduleMessageRepository.getById(lastMessageSentId++).setSent(true);
		return scheduleMessageRepository.getById(lastMessageSentId++);
	}
	
	/**
	 * La propiedad message cambia a true pues se les va a enviar un mensaje a los usuarios.
	 */
	private void setMessagePropertyToTrue() {
		List<User>users = userRepo.findAll();
		for (User user : users) {
			user.setMessage(true);
		}
	}

}
