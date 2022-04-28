package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

public class MessageSchedulingService {
	
	public static final List<String> TELL_OFF_MESSAGES = Arrays.asList(
			"Shame on you! You smoked last week! You've smoked {} cigarettes!",
			"What did you do?! You've already smoked {} cigarettes!!",
			"Please tell me it's not true...Tell me that you didn't smoke last week...You already smoked {} cigarettes...",
			"Please don't smoked again...You're better than this...You've smoked {} cigarettes sice you started using the app"
			);
	
	public static Integer TELL_OFF_MESSAGES_COUNTER = 0;
	public static Integer ENCOURAGING_MESSAGES_COUNTER = 0;

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepo userRepo;

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
		//LocalDate lastWeek = LocalDate.now().minus(7, ChronoUnit.DAYS);
		List<User> users = userService.findUsers();

		for (User user : users) {
			user.setMessage(true);//En el front tengo que hacer petición para setearla a false cuando el user lea el mensaje
			userRepo.save(user);
		}
	}

}
