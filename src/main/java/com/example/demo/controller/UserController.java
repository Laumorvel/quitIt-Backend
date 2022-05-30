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

import com.example.demo.error.AchievementAlreadyAddedException;
import com.example.demo.error.AchievementNotAddedException;
import com.example.demo.error.AchievementNotFoundException;
import com.example.demo.error.AlreadySetAsAnSmokingDayException;
import com.example.demo.error.ApiError;
import com.example.demo.error.PenaltyAlreadyAddedException;
import com.example.demo.error.PenaltyNotAddedException;
import com.example.demo.error.PenaltyNotFoundException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Achievement;
import com.example.demo.model.Message;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PenaltyService;
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
	
	@Autowired PenaltyService penaltyService;

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
	 * 
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
	public User updateUser(@RequestParam(required = false) Integer cigarettes, 
			@RequestParam(required = false) Double money, @RequestParam(required = false) Boolean reset,
			@RequestParam(required = false) Boolean message, @RequestParam(required = false) String urlImage,  @RequestBody String password) {
		
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepo.findByEmail(email);

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
		} else if (password != null) {
			return userService.changePass(user, password);
		} 
		
			else {
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
	public void deleteUser(@PathVariable Long idDelete) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepo.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException();
		} else {
			userService.borrarUsuario(idDelete); //QUE DEVUELVE SI  SE BORRA
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
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String password) {
		
		String mail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(mail);
		
		
		if (username == null & password==null) {
			return userService.getUserEmail(email);
		} else if (email == null & password==null) {
			return userService.getUsernameComplete(username);
		}
		else {
			return userService.getUserPassword(password, result);
		}
	}

	/**
	 * Comprueba que los usuarios coincidan con el nombre introducido. Si es el
	 * username, los usuarios devueltos serán los que el usuario puede agregar como
	 * amigos (no son sus amigos aún y coinciden en username). Si es el friend, los
	 * usuarios devueltos serán aquellos que coinciden en username y ya son amigos
	 * del usuario. En caso de tener groupMembers, estos serán los que conforman el
	 * grupo que se está creando, para que no se agregue dos veces al mismo amigo.
	 * 
	 * @return lista de usuariios
	 */
	@GetMapping("/users")
	public List<User> getAllUsersRanking(@RequestParam(required = false) String username,
			@RequestParam(required = false) String friend, @RequestBody(required = false) List<User> groupMembers) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			if (username != null) {
				return userService.getUsername(username, result.getId());
			} else if (friend != null) {
				return userService.getFriendUsername(friend, result.getId(), groupMembers);
			} else {
				return userService.getAllUsers();
			}
		}

	}
	
	@PostMapping("/users")
	public List<User> getUsersWithMembers(@RequestParam(required= false) String friend, @RequestBody(required = false) List<User> groupMembers){
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);
		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return userService.getFriendUsername(friend, result.getId(), groupMembers);
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
		datos.setToUser("quititdaw@gmail.com");

		smtpMailSender.send(datos.getToUser(), datos.getSubject(), datos.getText(), datos.getFromUser());
	}
	
	/**
	 * Añade un achievement al usuario
	 * @param achievement
	 * @return usuario modificado
	 */
	@PostMapping("/achievement/user")
	public User addAchievementToUser(@RequestBody Achievement achievement) {
		User user;
		try {
			user = userRepo.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		}catch(Exception e) {
			throw new UserNotFoundException();
		}
		return userService.addAchievementToUser(achievement, user);
	}
	
	@DeleteMapping("/achievement/{id}/user")
	public void removeAchievementFromUser(@PathVariable Long id) {
		User user;
		try {
			user = userRepo.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		}catch(Exception e) {
			throw new UserNotFoundException();
		}
		userService.deleteAchievementOfUser(id, user);
	}
	
	@DeleteMapping("/penalty/{id}/user")
	public void removePenaltyFromUser(@PathVariable Long id) {
		User user;
		try {
			user = userRepo.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		}catch(Exception e) {
			throw new UserNotFoundException();
		}
		penaltyService.deletePenaltyFromUser(user, id);
	}
	
	@PostMapping("/penalty/user")
	public User addPenaltyToUser(@RequestBody Penalty penalty) {
		User user;
		try {
			user = userRepo.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		}catch(Exception e) {
			throw new UserNotFoundException();
		}
		return userService.addPenaltyToUser(user, penalty.getId());
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
	
	@ExceptionHandler(AchievementNotFoundException.class)
	public ResponseEntity<ApiError> AchievementNotFoundException(AchievementNotFoundException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}
	
	@ExceptionHandler(AchievementAlreadyAddedException.class)
	public ResponseEntity<ApiError> AchievementAlreadyAddedException(AchievementAlreadyAddedException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	@ExceptionHandler(AchievementNotAddedException.class)
	public ResponseEntity<ApiError> AchievementNotAddedException(AchievementNotAddedException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	@ExceptionHandler(PenaltyNotAddedException.class)
	public ResponseEntity<ApiError> PenaltyNotAddedException(PenaltyNotAddedException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	@ExceptionHandler(PenaltyAlreadyAddedException.class)
	public ResponseEntity<ApiError> PenaltyAlreadyAddedException(PenaltyAlreadyAddedException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}
	
	@ExceptionHandler(PenaltyNotFoundException.class)
	public ResponseEntity<ApiError> PenaltyNotFoundException(PenaltyNotFoundException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

}
