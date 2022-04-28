package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ScheduledMessage;
import com.example.demo.repository.ScheduledMessageRepository;

@Service
public class ScheduledMessageService {

	@Autowired
	ScheduledMessageRepository scheduleMessageRepository;

	/**
	 * Devuelve el siguiente mensaje de la lista. Esto se marca por el mensaje que
	 * tenga la propiedad sent a true.
	 * El mensaje a enviar se seteará a true mientras que el anterior enviado pasará a false
	 * 
	 * @return mensaje a enviar seteado a true
	 */
	public ScheduledMessage getScheduledMessage() {
		Long lastMessageSentId = scheduleMessageRepository.getLastMessageSentId() < 15
				? scheduleMessageRepository.getLastMessageSentId()
				: 0;
		scheduleMessageRepository.getLastMessageSent().setSent(false);
		scheduleMessageRepository.getById(lastMessageSentId++).setSent(true);
		return scheduleMessageRepository.getById(lastMessageSentId++);
	}

}
