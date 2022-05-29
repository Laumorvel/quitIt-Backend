package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.NonExistentAchievementException;
import com.example.demo.model.Achievement;
import com.example.demo.model.User;
import com.example.demo.repository.AchievementRepo;
import com.example.demo.repository.UserRepo;

@Service
public class AchievementService {

	@Autowired
	AchievementRepo achievementRepo;
	
	@Autowired
	UserRepo userRepo;

	/**
	 * Devuelve una lista de todos los logros existentes con las imágenes adecuadas
	 * dependiendo del usuario
	 * 
	 * @return logros comprobados
	 */
	public List<Achievement> getAllAchievement() {
		return achievementRepo.findAllAchievement();
	}
	
	/**
	 * Consigue la lista de logros de un usuario concreto
	 * @param user
	 * @return lista de logros
	 */
	public List<Achievement>getUserAchievements(User user){
		return userRepo.findById(user.getId()).get().getAchievementList();
	}

	/**
	 * Devuelve un logro concreto. Devuelve una excepción en caso de que no se
	 * encuentre el logro.
	 * 
	 * @param id
	 * @return logro
	 */
	public Achievement getAchievement(Long id) {
		try {
			return achievementRepo.findById(id).get();
		} catch (Exception e) {
			throw new NonExistentAchievementException();
		}
	}
	
	/**
	 * Modifica la lista de logros del usuario
	 * @param achievements
	 * @param user
	 * @return usuario con lista de logros modificados
	 */
	public User modifyAchivementsFromUser(List<Achievement>achievements, User user) {
		try {
			userRepo.findById(user.getId()).get().setAchievementList(achievements);
			return userRepo.save(user);
		}catch (Exception e) {
			throw new NonExistentAchievementException();
		}
	}
	
	/**
	 * Modifica un logro
	 * @param achievement
	 * @param id
	 * @return logro modificado
	 */
	public Achievement modifyAchievement(Achievement achievement, Long id) {
			if(achievementRepo.getById(id) == null) {
				throw new NonExistentAchievementException();
			}
			achievement.setId(id);
			return achievementRepo.save(achievement);
	}
	
	/**
	 * Añade un nuevo logro
	 * @param achievement
	 * @return logro añadido
	 */
	public Achievement addNewAchievement(Achievement achievement) {
		return achievementRepo.save(achievement);
	}

	
}
