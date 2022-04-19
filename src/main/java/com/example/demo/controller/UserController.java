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
import com.example.demo.error.CommentNotExist;
import com.example.demo.error.IncidenceNotExist;
import com.example.demo.error.TypeMismatchException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Achievement;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.Incidence;
import com.example.demo.model.MeetUp;
import com.example.demo.model.Message;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AchievementService;
import com.example.demo.service.CommentsCommunityService;
import com.example.demo.service.IncidenceService;
import com.example.demo.service.MeetUpService;
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
	private CommentsCommunityService commentsCommunityService;

	@Autowired
	private IncidenceService incidenceService;

	@Autowired
	private MeetUpService meetUpService;

	@Autowired
	private AchievementService achievementService;

	@Autowired
	private PenaltyService penaltyService;

	@Autowired
	private SmtpMailSender smtpMailSender;

	/**
	 * Devuelve el usuario
	 * 
	 * @return
	 */
	@GetMapping("/user")
	public User getUser(@RequestParam (required=false) String username) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			if(username!=null) {
				return userService.getUsername(username);
			}
			else {
				userService.setUser(result);
				return result;
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
	 * @param cigarettes
	 * @return usuario actualizado
	 */
	@PutMapping("/user")
	public User updateUser(@RequestParam(required = false) Integer cigarettes , @RequestBody User user1,
			@RequestParam(required = false) Double money, @RequestParam(required = false) Boolean reset) {
		User user = userRepo.findByEmail(user1.getEmail());

		if (user == null) {
			throw new UserNotFoundException();
		}

		if (money != null) {
			return userService.modificaDatosIniciales(cigarettes, user, money);
		} else if (reset != null) {
			return userService.resetUser(user);
		} else {
			return userService.updateUserAfertSmoking(cigarettes, user);
		}
	}
	
	
	/**
	 * Borra un usuario por su id
	 * @param idDelete
	 * @return
	 */
	@DeleteMapping("/user/{idDelete}")
	public User deleteUser(@PathVariable Long idDelete) {
		
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long result =  userRepo.findByEmail(email).getId();
        
		
		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return userService.borrarUsuario(idDelete);
		}
	}
	
	/**
	 * Comprueba si el email o el username del usuario a esta registrado en la base de datos
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
			return userService.getUsername(username);
		}
	}

	/**
	 * Da la lista de comentarios de la comunidad
	 * 
	 * @return
	 */
	@GetMapping("/commentsCommunity")
	public List<CommentCommunity> getComments() {
		return commentsCommunityService.getComments();

	}

	/**
	 * Muestra el comentario con la id indicada
	 * 
	 * @param idC
	 * @return
	 */
	@GetMapping("/commentsCommunity/{idC}")
	public CommentCommunity getCommentById(@PathVariable Long idC) {
		CommentCommunity comment = incidenceService.getCommentById(idC);

		if (comment == null) {
			throw new IncidenceNotExist((long) idC);
		} else {
			return comment;
		}

	}

	/**
	 * Crea un comentario en el chat de la comunidad
	 * 
	 * @param datos
	 * @return
	 */
	@PostMapping("/commentsCommunity")
	public CommentCommunity addCommentsCommunity(@RequestBody CommentCommunity datos) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return this.commentsCommunityService.addCommentCommunity(result, datos);
		}
	}

	/**
	 * Borra los comentarios de la comunidad
	 * 
	 * @param idC
	 * @return
	 */
	@DeleteMapping("/commentsCommunity/{idC}")
	public ResponseEntity<?> deleteCommentsCommunity(@PathVariable Long idC) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = userRepo.findByEmail(email).getId();

		CommentCommunity result = commentsCommunityService.delete(idC);

		if (result == null) {
			throw new CommentNotExist(id);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * Da la lista de incidencias
	 * 
	 * @return
	 */
	@GetMapping("/incidence")
	public List<Incidence> getAllIncidences() {
		return incidenceService.getAllIncidences();
	}

	/**
	 * Crea una incidencia
	 * 
	 * @param datos
	 * @return
	 */
	@PostMapping("/incidence")
	public Incidence createIncidence(@RequestBody Incidence datos) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return this.incidenceService.createIncidence(result, datos);
		}
	}

	/**
	 * Edita la incidencia añadiendole un comentario
	 * 
	 * @param idi
	 * @param comentario
	 * @return
	 */
	@PutMapping("/incidence/{idi}")
	public Incidence editIncidence(@PathVariable Long idi, @RequestBody (required=false) CommentCommunity comentario, @RequestParam (required=false) String state) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			Incidence incidence = incidenceService.findById(idi);
			if(state!=null) {
				if (incidence == null) {
					throw new IncidenceNotExist(idi);
				} else {
					return incidenceService.changeState(state, incidence);
				}
			}
			else {
				if (incidence == null) {
					throw new IncidenceNotExist(idi);
				} else {
					
					return incidenceService.editIncidence(idi, comentario);
				}
			}
			
		}
	}

	/**
	 * Da la lista de usuarios
	 * 
	 * @return
	 */
	@GetMapping("/users")
	public List<User> getAllUsersRanking() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);
		userService.setUser(result);
		return userService.getAllUsers();
	}

	/**
	 * Da la lista de meet ups
	 * 
	 * @return
	 */
	@GetMapping("/meetUp")
	public List<MeetUp> getAllMeetUps() {
		return meetUpService.getAllMeetUps();
	}

	/**
	 * Da la lista de logros
	 * 
	 * @return
	 */
	@GetMapping("/achievement")
	public List<Achievement> getAllAchievement() {
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email); 
		}catch (Exception e) {
			throw new UserNotFoundException();
		}
		return achievementService.getAllAchievement(user);
	}

	/**
	 * Da la lista de penalizaciones
	 * 
	 * @return
	 */
	@GetMapping("/penalty")
	public List<Penalty> getAllPenalty() {
		return penaltyService.getAllPenalty();
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

	/**
	 * Excepción que muestra que la incidencia no existe
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(IncidenceNotExist.class)
	public ResponseEntity<ApiError> IncidenceNotFound(IncidenceNotExist ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.NOT_FOUND);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
	}

	
	@ExceptionHandler(TypeMismatchException.class)
	public ResponseEntity<ApiError> IncidenceNotFound(TypeMismatchException ex) throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

}
