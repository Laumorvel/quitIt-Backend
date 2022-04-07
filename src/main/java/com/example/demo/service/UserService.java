package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	public User getUserEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public User getUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> getAllUsers() {
		return userRepo.findAllUsers();
	}

	/**
	 * Actualiza la información del usuario en bbdd cuando lo carga
	 */
	public User setUser(User user) {
		user.setUserInitSession();
		return userRepo.save(user);
	}

	/**
	 * Actualiza la información del usuario tras haber fumado, indicando los
	 * cigarrillos que ha fumado
	 * 
	 * @param cigarettes
	 * @param user
	 * @return usuario actualizado
	 */
	public User updateUserAfertSmoking(Integer cigarettes, User user) {

		user.resetUserAfterSmoking(cigarettes);
		return userRepo.save(user);
	}

	/**
	 * Modifica los datos iniciales del usuario (cigarrillos diarios previos y
	 * dinero que gastaba)
	 * 
	 * @param cigarettes
	 * @param user
	 * @param money
	 * @return usuario con los datos modificados
	 */
	public User modificaDatosIniciales(Integer cigarettes, User user, Double money) {
		user.setMoneyPerDay(money);
		user.setCigarettesBeforePerDay(cigarettes);
		user.setUserInitSession();
		return userRepo.save(user);
	}

	/**
	 * Resetea la información del usuario como si volviera a empezar a usar la
	 * aplicación
	 * 
	 * @param user
	 * @return usuario con muchos de sus valores a 0 - excepto los del registro
	 *         (datos personales y de exfumador)
	 */
	public User resetUser(User user) {
		user.setDaysInARowWithoutSmoking(0);
		user.setCigarettesAvoided(0);
		user.setTotalTimeWithoutSmoking(0);
		user.setMoneySaved(0);
		user.setSmokingDays(0);
		user.setCigarettesSmoked(0);
		user.setLastDateSmoking(null);
		user.setStartDate(LocalDate.now());
		return userRepo.save(user);
	}
}
