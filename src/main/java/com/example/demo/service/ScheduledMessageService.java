package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ScheduledMessage;
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
	 * tenga la propiedad sent a true. El mensaje a enviar se seteará a true
	 * mientras que el anterior enviado pasará a false
	 * 
	 * @return mensaje a enviar seteado a true
	 */
	public ScheduledMessage getScheduledMessageToSend() {
		Long lastMessageSentId = scheduleMessageRepository.getLastMessageSentId() < 16
				? scheduleMessageRepository.getLastMessageSentId()
				: 0;
		// Se configura el mensaje anteriormente mandado
		ScheduledMessage previousMsg = scheduleMessageRepository.getLastMessageSent();
		previousMsg.setSent(false);
		scheduleMessageRepository.save(previousMsg);
		// Se configura el mensaje que se va a mandar
		ScheduledMessage toSend = scheduleMessageRepository.findById(lastMessageSentId + 1).get();
		toSend.setSent(true);
		return scheduleMessageRepository.save(toSend);
	}

}
