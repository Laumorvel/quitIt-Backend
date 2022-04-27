package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.model.User;

public class MessageSchedulingService {

	@Autowired
	UserService userService;

	/**
	 * Todos los lunes a medianoche se ejecuta la tarea programada. Es necesario
	 * traer a los usuarios al completo pues se le modificará una propiedad y se
	 * deben volver a guardar. Además, se necesita conocer el valor de sus
	 * propiedades para poder enviar un mensaje u otro.
	 * 
	 * Los mensajes se mandarán dependiendo del avance del usuario de la semana
	 * anterior.
	 */
	// @Scheduled(fixedRate = 10000)
	@Scheduled(cron = "0 0 0 * * MON")
	public void sendMessage() {
		LocalDate lastWeek = LocalDate.now().minus(7, ChronoUnit.DAYS);
		List<User> users = userService.findUsers();

		for (User user : users) {
			if (user.getLastDateSmoking().isAfter(lastWeek)) {

			}
		}
	}

}
