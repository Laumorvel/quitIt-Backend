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
	CommandLineRunner initData(UserRepo repositorioUsers, CommentsCommunityRepo commentsCommunityRepo,
			MeetUpRepo meetUpRepo, AchievementRepo achievementRepo, PenaltyRepo penaltyRepo) {

		// Usuarios administradores
		User usuario1 = new User("Adela", "Lira", "adelalira", "adela@gmail.com", passwordEncoder.encode("12345"),
				"ADMIN", 3, 4.15);

		User usuario2 = new User("Laura", "Moreno", "lauramoreno", "laura@gmail.com", passwordEncoder.encode("12345"),
				"ADMIN", 7, 2.0);

		// Usuarios usuarios
		User usuarioUser1 = new User("Loli", "Montero", "lolimontero", "loli@gmail.com",
				passwordEncoder.encode("12345"), "USER", 17, 9.0);

		User usuarioUser2 = new User("Pepe", "Lopez", "pepitoloooooopez", "pepe@gmail.com",
				passwordEncoder.encode("12345"), "USER", 30, 10.30);

		usuario1.setStartDate(fecha3);
		usuario2.setStartDate(fecha3);
		usuarioUser1.setStartDate(fecha3);

		usuarioUser2.setStartDate(fecha3);

		// Comentarios de la comunidad
		CommentCommunity comentarioComunidad1 = new CommentCommunity("Buenos dias!", usuarioUser1, fecha1);

		CommentCommunity comentarioComunidad2 = new CommentCommunity("Otro día más sumando!!!", usuarioUser2, fecha1);

		// MeetUps
		MeetUp meetUp1 = new MeetUp("Quedada para cervecita",
				"Vamos a echar unas cervecitas al sol el sabado por el centro de Sevilla, apuntante!", fecha2,
				"100 Montaditos frente al rectorado");

		// Logros
		Achievement logro1 = new Achievement("First cross on the calendar", "No smoking for one day", "certified.png", 1, "days");
		Achievement logro2 = new Achievement("Step by step", "No smoking for 2 days", "4497660.png", 2, "days");
		Achievement logro3 = new Achievement("To infinity and beyond", "5 cigarettes non-smoked", "exito.png", 5, "cigarettes");
		Achievement logro4 = new Achievement("Superpowers", "You saved 20€", "growth.png", 20, "money");
		Achievement logro5 = new Achievement("Home is where my plants are", "No smoking for 5 days",
				"high-quality.png", 5 ,"days");
		Achievement logro6 = new Achievement("Saturday night fever", "10 cigarettes non-smoked", "imagination.png", 10, "cigarettes");
		Achievement logro7 = new Achievement("Following the road", "No smoking for one week", "motivation.png", 7, "days");
		Achievement logro8 = new Achievement("Clothes off", "15 cigarettes non-smoked", "premium.png", 15, "cigarettes");
		Achievement logro9 = new Achievement("Less paper", "No smoking for 10 days", "prime.png", 10, "days");
		Achievement logro10 = new Achievement("Jump around", "20 cigarettes non-smoked", "struggle.png", 20, "cigarettes");
		Achievement logro11 = new Achievement("Crush the calendar", "No smoking for 2 weeks", "taekwondo.png", 14, "days");
		Achievement logro12 = new Achievement("Piggy bank", "You saved 100€", "piggybank.png", 100, "money");
		Achievement logro13 = new Achievement("Ballet on the city", "50 cigarettes non-smoked", "victoria.png", 50, "cigarettes");
		Achievement logro14 = new Achievement("First podium", "No smoking for 100 days", "success.png", 100, "days");
		Achievement logro15 = new Achievement("The mad vocalist", "No smoking for 100 days in a row", "karaoke.png", 100, "daysInARow");
		Achievement logro16 = new Achievement("A sky full of posibilities", "No smoking for 200 days in a row",
				"stars.png", 200, "daysInARow");
		Achievement logro17 = new Achievement("Filthy rich", "You saved 300€", "rich.png", 300, "money");
		Achievement logro18 = new Achievement("Reinforcing your foundations", "500 cigarettes non-smoked",
				"brickwall.png", 500, "cigarettes");
		Achievement logro19 = new Achievement("To the Moon and back", "No smoking for 300 days in a row",
				"startup.png", 300, "daysInARow");
		Achievement logro20 = new Achievement("Being your own vaccine", "500€ saved", "vaccinated.png", 500, "money");

		// Penalizaciones
		Penalty penalty1 = new Penalty("Rompes la racha de un día sin fumar", "Un día sin fumar");
		Penalty penalty2 = new Penalty("Rompes la racha de cinco días sin fumar", "Cinco días sin fumar");
		Penalty penalty3 = new Penalty("Rompes la racha de diez días sin fumar", "Diez días sin fumar");
		Penalty penalty4 = new Penalty("Rompes la racha de veinticiendo días sin fumar", "Veinticiendo días sin fumar");
		Penalty penalty5 = new Penalty("Rompes la racha de cincuenta días sin fumar", "Cincuenta días sin fumar");
		Penalty penalty6 = new Penalty("Rompes la racha de cien días sin fumar", "Cien días sin fumar");
		Penalty penalty7 = new Penalty("Rompes la racha de doscientos días sin fumar", "Doscientos días sin fumar");
		Penalty penalty8 = new Penalty("Ultimo logro para que sea par???", "Doscientos días sin fumar");

		return (args) -> {

			repositorioUsers.saveAll(Arrays.asList(usuario1, usuario2, usuarioUser1, usuarioUser2));

			commentsCommunityRepo.saveAll(Arrays.asList(comentarioComunidad1, comentarioComunidad2));

			meetUpRepo.saveAll(Arrays.asList(meetUp1));

			achievementRepo.saveAll(
					Arrays.asList(logro1, logro2, logro3, logro4, logro5, logro6, logro7, logro8, logro9, logro10,
							logro11, logro12, logro13, logro14, logro15, logro16, logro17, logro18, logro19, logro20));

			penaltyRepo.saveAll(
					Arrays.asList(penalty1, penalty2, penalty3, penalty4, penalty5, penalty6, penalty7, penalty8));

		};
	}

}
