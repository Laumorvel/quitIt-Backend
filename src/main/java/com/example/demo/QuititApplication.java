package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Achievement;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.MeetUp;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.AchievementRepo;
import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.MeetUpRepo;
import com.example.demo.repository.PenaltyRepo;
import com.example.demo.repository.UserRepo;

@SpringBootApplication
public class QuititApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(QuititApplication.class, args);
	}
	
	LocalDate fecha1 = LocalDate.parse("2022-06-20");
	LocalDateTime fecha2 = LocalDateTime.parse("2022-06-24T18:30:00");
	LocalDate fecha3 = LocalDate.parse("2022-02-20");

	@Bean
	CommandLineRunner initData (UserRepo repositorioUsers, CommentsCommunityRepo commentsCommunityRepo, MeetUpRepo meetUpRepo,
								AchievementRepo achievementRepo, PenaltyRepo penaltyRepo) {		
		
		//Usuarios administradores
		User usuario1 = new User("Adela","Lira","adelalira","adela@gmail.com", passwordEncoder.encode("12345"),
				"ADMIN", 3, 4.15 );
		
		User usuario2 = new User("Laura","Moreno","lauramoreno","laura@gmail.com", passwordEncoder.encode("12345"),
				"ADMIN", 7, 2.0 );
		
		//Usuarios usuarios
		User usuarioUser1 = new User("Loli","Montero","lolimontero","loli@gmail.com", passwordEncoder.encode("12345"),
				"USER", 17, 9.0);
		

		User usuarioUser2 = new User("Pepe","Lopez","pepitoloooooopez","pepe@gmail.com", passwordEncoder.encode("12345"),
				"USER", 30, 10.30);
		
		usuario1.setStartDate(fecha3);
		usuario2.setStartDate(fecha3);
		usuarioUser1.setStartDate(fecha3);

		usuarioUser2.setStartDate(fecha3);
		
		//Comentarios de la comunidad
		CommentCommunity comentarioComunidad1 = new CommentCommunity("Buenos dias!",usuarioUser1, fecha1);
		
		CommentCommunity comentarioComunidad2 = new CommentCommunity("Otro día más sumando!!!",usuarioUser2, fecha1);
		
		
		//MeetUps
		MeetUp meetUp1 = new MeetUp("Set for beer","Let's take some beer to the sun on Saturday through downtown Seville, prompt!",
									fecha2,"100 Montaditos Against Rectorate");
				
		//Logros
		Achievement logro1 = new Achievement("Un día sin fumar","A day without smoking");
		Achievement logro2 = new Achievement("Cinco días sin fumar","Five days without smoking");
		Achievement logro3 = new Achievement("Diez días sin fumar","Ten days without smoking");
		Achievement logro4 = new Achievement("Veinticiendo días sin fumar","Twenty-two days without smoking");
		Achievement logro5 = new Achievement("Cincuenta días sin fumar","Fifty days without smoking");
		Achievement logro6 = new Achievement("Cien días sin fumar","One hundred days without smoking");
		Achievement logro7 = new Achievement("Doscientos días sin fumar","Two hundred days without smoking");
		Achievement logro8 = new Achievement("Ultimo logro para que sea par???","Five hundred days without smoking");
		
		
		//Penalizaciones
		Penalty penalty1 = new Penalty("Rompes la racha de un día sin fumar","Break the one-day streak without smoking");
		Penalty penalty2 = new Penalty("Rompes la racha de cinco días sin fumar","Break the five-day streak without smoking");
		Penalty penalty3 = new Penalty("Rompes la racha de diez días sin fumar","Break the ten-day streak without smoking");
		Penalty penalty4 = new Penalty("Rompes la racha de veinticiendo días sin fumar","Break the rack of twenty-five days without smoking");
		Penalty penalty5 = new Penalty("Rompes la racha de cincuenta días sin fumar","Break the streak of fifty days without smoking");
		Penalty penalty6 = new Penalty("Rompes la racha de cien días sin fumar","Break the one hundred days without smoking");
		Penalty penalty7 = new Penalty("Rompes la racha de doscientos días sin fumar","Break the two hundred days without smoking");
		Penalty penalty8 = new Penalty("Ultimo logro para que sea par???","Break the five hundred days without smoking");
				
		return (args) -> {

			repositorioUsers.saveAll(Arrays.asList(usuario1, usuario2, usuarioUser1,usuarioUser2));
			
			commentsCommunityRepo.saveAll(Arrays.asList(comentarioComunidad1, comentarioComunidad2));

			meetUpRepo.saveAll(Arrays.asList(meetUp1));
			
			achievementRepo.saveAll(Arrays.asList(logro1, logro2, logro3, logro4, logro5, logro6, logro7, logro8));
			
			penaltyRepo.saveAll(Arrays.asList(penalty1, penalty2, penalty3, penalty4, penalty5, penalty6, penalty7, penalty8));

			
		};
	}


	
	
	
	
}
