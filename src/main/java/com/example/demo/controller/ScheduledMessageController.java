package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ScheduledMessage;
import com.example.demo.service.ScheduledMessageService;

@RestController
public class ScheduledMessageController {

	@Autowired
	private ScheduledMessageService scheduledMessageService;

	/**
	 * Consigue el mensaje que se debe enviar cambiando la propiedad del usuario que
	 * indica que se le ha enviado un mensaje.
	 * 
	 * @return mensaje a enviar al usuario
	 */
	@GetMapping("/scheduledMessage")
	public ScheduledMessage getScheduledMessage() {
		return scheduledMessageService.getScheduledMessageToSend();
	}

}
