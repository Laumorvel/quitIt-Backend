package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OrdenarPorNumero;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	/**
	 * Busca un usuario por email
	 * @param email
	 * @return
	 */
	public User getUserEmail(String email) {
		return userRepo.findByEmail(email);
	}

	/**
	 * Busca un usuario por su username
	 * @param username
	 * @return
	 */
	public User getUsernameComplete(String username) {
		return userRepo.findByUsernameComplete(username);
	}
	
	public User getUsername(String username) {
		return userRepo.findByUsername(username);
	}

	/**
	 * Muestra todos los usuarios ordenados para el ranking
	 * @return
	 */
	public List<User> getAllUsers() {
		List<User> listaUsuarios = userRepo.findAllUsers();
		List<User> listaUsuariosOrdenada = new ArrayList<>();
		Collections.sort(listaUsuarios, new OrdenarPorNumero());
		for (User e : listaUsuarios) {
			listaUsuariosOrdenada.add(e);
		}
		
		return  listaUsuariosOrdenada;
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

	/**
	 * Borra todos los datos del usuario en cascada
	 * @param result
	 * @return
	 */
	public User borrarUsuario(Long result) {
		if (userRepo.existsById(result)) {
						
			
			User user = userRepo.findById(result).orElse(null);
			User usuarioParaImprimir = userRepo.findById(result).orElse(null);
			
			user.setAchievementList(null);
			user.setGroupList(null);
			user.setFile(null);
			user.setPenalties(null);
			user.setUserList(null);
			
			
			userRepo.delete(user);
			
			return usuarioParaImprimir;
		} else {
			return null;
		}
	}
	
	
	
}
