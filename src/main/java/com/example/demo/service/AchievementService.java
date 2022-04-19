package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Achievement;
import com.example.demo.model.User;
import com.example.demo.repository.AchievementRepo;

@Service
public class AchievementService {

	@Autowired
	AchievementRepo achievementRepo;

	/**
	 * Devuelve una lista de todos los logros existentes con las imágenes adecuadas
	 * dependiendo del usuario
	 * 
	 * @return logros comprobados
	 */
	public List<Achievement> getAllAchievement(User user) {
		List<Achievement> achievements = achievementRepo.findAllAchievement();

		return checkAchievements(achievements, user);
	}

	/**
	 * Modifica las imágenes de los achievements. Si se han logrado serán en color,
	 * en caso contrario en blanco y negro. Se establece también el porcentaje que
	 * indica el avance del logro
	 * 
	 * @param achievements
	 * @param user
	 * @return lista modificada de logros según el usuario indicado
	 */
	private List<Achievement> checkAchievements(List<Achievement> achievements, User user) {
		// 1 - no fuma durante 1 día
		if (user.getTotalTimeWithoutSmoking() >= 1) {
			achievements.get(0).setImg("certified.png");
			achievements.get(0).setPorcentaje("100");
		} else {
			achievements.get(0).setImg("certifiedBW.png");
			achievements.get(0).setPorcentaje("0");
		}

		// 2 - no fuma durante 2 días
		if (user.getTotalTimeWithoutSmoking() >= 2) {
			achievements.get(0).setImg("4497660.png");
			achievements.get(0).setPorcentaje("100");
		} else {
			achievements.get(0).setImg("4497660BW.png");
			achievements.get(0).setPorcentaje(calculatePercentage(user.getTotalTimeWithoutSmoking(), 2));
		}

		return achievementRepo.saveAll(achievements);
	}

	/**
	 * Calcula el porcentaje conseguido de un logro en caso de logros relacionados
	 * con días
	 * 
	 * @param dias
	 * @return porcentaje conseguido de un logro
	 */
	private String calculatePercentage(Integer diasTotalesUser, Integer objetivo) {
		Integer porcentaje = (diasTotalesUser * 100) / objetivo;
		return Integer.toString(porcentaje);
	}

}
