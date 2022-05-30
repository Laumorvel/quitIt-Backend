package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.ApiError;
import com.example.demo.error.NonExistentAchievementException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Achievement;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AchievementService;

@RestController
public class AchievementController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AchievementService achievementService;

	/**
	 * Da la lista de logros
	 * 
	 * @return todos los achievements
	 */
	@GetMapping("/achievement")
	public List<Achievement> getAllAchievement() {
		return achievementService.getAllAchievement();
	}

	/**
	 * Consigue la lista de logros de un usuario
	 * 
	 * @return logros
	 */
	@GetMapping("/user/achievement")
	public List<Achievement> getUserAchievements() {
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email);
			return achievementService.getUserAchievements(user);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
	}

	/**
	 * Consigue un logro concreto
	 * 
	 * @param id
	 * @return un logro
	 */
	@GetMapping("/achievement/{id}")
	public Achievement getAchievement(@PathVariable Long id) {
		return achievementService.getAchievement(id);
	}

	/**
	 * Modifica los logros de un usuario
	 * 
	 * @param id
	 * @return usuario con la lista de logros modificada
	 */
	@PutMapping("/user/achievements")//"user/{id}/achievement"
	public User modifyAchivementsFromUser(@RequestBody List<Achievement> achievements) {
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email);
			return achievementService.modifyAchivementsFromUser(achievements, user);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
	}


	/**
	 * Modifica un logro concreto
	 * 
	 * @param achievement
	 * @param id
	 * @return logro modificado
	 */
	@PutMapping("/achievement/{id}")
	public Achievement modifyAchievement(@RequestBody Achievement achievement, @PathVariable Long id) {
		return modifyAchievement(achievement, id);
	}

	/**
	 * Añade un nuevo logro
	 * 
	 * @param achievement
	 * @return logro añadido
	 */
	@PostMapping("/achievement")
	public Achievement addAchievement(@RequestBody Achievement achievement) {
		return achievementService.addNewAchievement(achievement);
	}
	
	/**
	 * Maneja la traza de la excepción que se produce cuando no existe el logro que
	 * se quiere consultar
	 * 
	 * @param ex
	 * @return traza controlada de la excepción
	 * @throws Exception
	 */
	@ExceptionHandler(NonExistentAchievementException.class)
	public ResponseEntity<ApiError> NonExistentAchievementException(NonExistentAchievementException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}


}
