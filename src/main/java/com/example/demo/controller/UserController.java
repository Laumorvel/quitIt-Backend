package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.AlreadySetAsAnSmokingDayException;
import com.example.demo.error.ApiError;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.SmtpMailSender;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private SmtpMailSender smtpMailSender;

	/**
	 * Devuelve el usuario
	 * 
	 * @return
	 */
	@GetMapping("/user")
	public User getUser() {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return userService.setUser(result);
		}

	}

	
	/**
	 * Añadir usuario a tu lista de amigos.
	 * @param userRecibido
	 * @return
	 */
	@PostMapping("/user")
	public User addfriend(@RequestBody(required = false) User userRecibido) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			if (userRecibido != null) {
				return this.userService.addfriend(result, userRecibido);
			} else {
				throw new UserNotFoundException();
			}
		}
	}

	/**
	 * 
	 * Actualiza la información del usuario una vez que este fuma de nuevo
	 * inndicando los cigarrillos que ha fumado. Es posible actualizar este dato más
	 * de una vez puesto que el usuario puede anota que ha fumado varias veces el
	 * mismo día
	 * 
	 * En caso de incluir dinero, se modificarán los datos iniciales del usuario
	 * 
	 * En caso de incluir reset, se resetearán los datos del usuario
	 * 
	 * En caso de incluir message, se seteará la propiedad message de los usuarios a
	 * false para indicar que se les ha mandado un mensaje
	 * 
	 * @param cigarettes
	 * @return usuario actualizado
	 */
	@PutMapping("/user")
	public User updateUser(@RequestParam(required = false) Integer cigarettes, @RequestBody User user1,
			@RequestParam(required = false) Double money, @RequestParam(required = false) Boolean reset,
			@RequestParam(required = false) Boolean message, @RequestParam(required = false) String urlImage) {
		User user = userRepo.findByEmail(user1.getEmail());

		if (user == null) {
			throw new UserNotFoundException();
		}

		if (money != null) {
			return userService.modificaDatosIniciales(cigarettes, user, money);
		} else if (reset != null) {
			return userService.resetUser(user);
		} else if (cigarettes != null) {
			return userService.updateUserAfertSmoking(cigarettes, user);
		} else if (message != null) {
			return userService.setPropertyMessageToFalse(user);
		} else {
			return userService.setUrlImage(user, urlImage);
		}
	}

	/**
	 * Borra un usuario por su id
	 * 
	 * @param idDelete
	 * @return
	 */
	@DeleteMapping("/user/{idDelete}")
	public User deleteUser(@PathVariable Long idDelete) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long result = userRepo.findByEmail(email).getId();

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return userService.borrarUsuario(idDelete);
		}
	}

	/**
	 * Comprueba si el email o el username del usuario a esta registrado en la base
	 * de datos
	 * 
	 * @param email
	 * @param username
	 * @return
	 */
	@GetMapping("/email")
	public User checkEmailUsers(@RequestParam(required = false) String email,
			@RequestParam(required = false) String username) {
		if (username == null) {
			return userService.getUserEmail(email);
		} else {
			return userService.getUsernameComplete(username);
		}
	}

	/**
	 * Da la lista de usuarios
	 * 
	 * @return
	 */
	@GetMapping("/users")
	public List<User> getAllUsersRanking(@RequestParam(required = false) String username) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			if (username != null) {
				return userService.getUsername(username, result.getId());
			} else {
				return userService.getAllUsers();
			}
		}

	}

	/**
	 * Envia un email al correo de la empresa
	 * 
	 * @param datos
	 * @throws MessagingException
	 */
	@PostMapping("/mail")
	public void sendEmail(@RequestBody Message datos) throws MessagingException {
		datos.setToUser("aalira.96@gmail.com");

		smtpMailSender.send(datos.getToUser(), datos.getSubject(), datos.getText(), datos.getFromUser());
	}

	// EXCEPCIONES--------------------------------------------------------

	@ExceptionHandler(AlreadySetAsAnSmokingDayException.class)
	public ResponseEntity<ApiError> alreadySetAsAnSmokingDayException(AlreadySetAsAnSmokingDayException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

	/**
	 * Excepción que muestra que el usuario no existe
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> userNotFound(UserNotFoundException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

	

	
}
