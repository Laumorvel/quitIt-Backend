package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	public User findUserByUsername(String busqueda) {
		System.out.println(busqueda);
		return userRepo.findUserByUsername(busqueda);
	}
}
