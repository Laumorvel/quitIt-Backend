package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.error.PasswordException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.CommentsGroup;
import com.example.demo.model.MeetUp;
import com.example.demo.model.OrdenarPorNumero;
import com.example.demo.model.User;
import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.CommentsGroupRepo;
import com.example.demo.repository.MeetUpRepo;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired UserRepo userRepo;
	
	@Autowired CommentsCommunityRepo commentsCommutinyRepo;
	
	@Autowired CommentsGroupRepo CommentsGroupRepo;
	
	@Autowired MeetUpRepo meetUpRepo;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Busca un usuario por email
	 * 
	 * @param email
	 * @return
	 */
	public User getUserEmail(String email) {
		return userRepo.findByEmail(email);
	}

	/**
	 * Busca un usuario por su username
	 * 
	 * @param username
	 * @return
	 */
	public User getUsernameComplete(String username) {
		return userRepo.findByUsernameComplete(username);
	}

	/**
	 * Encuentra a los usuarios registrados, exceptuando al propio usuario logueado.
	 * Descarta a aquellos usuarios que ya sean amigos del usuario logueado.
	 * 
	 * @param username
	 * @param idUser
	 * @return usuarios candidatos a ser amigos
	 */
	public List<User> getUsername(String username, Long idUser) {
		List<User> usuariosCoincidentes = userRepo.findByUsername(username, idUser);// no incluye al propio usuario
		// Elimina los usuarios que sean administradores
		for (User user : usuariosCoincidentes) {
			if (user.getRol().equals("ADMIN")) {
				usuariosCoincidentes.remove(usuariosCoincidentes.indexOf(user));
			}
		}
		for (User user : usuariosCoincidentes) {
			Long idFriend = userRepo.findUsersToAddFriends(idUser, user.getId());
			if (idFriend != null) {
				usuariosCoincidentes.remove(usuariosCoincidentes.indexOf(user));// elimina de la lista al usuario que ya
																				// sea amigo
			}
		}
		return usuariosCoincidentes;
	}

	/**
	 * Encuentra a los amigos del usuario que realiza la búsqueda por el nombre de
	 * usuario introducido. Descarta a aquellos usuarios que ya se hayan incluido en
	 * el grupo que se esté formando si así fuera-
	 * 
	 * @param username
	 * @param idUser
	 * @return lista de usuarios
	 */
	public List<User> getFriendUsername(String username, Long idUser, List<User> groupMembers) {
		List<User> friends = userRepo.findFriendsByUsername(username, idUser);
		if (groupMembers != null && !groupMembers.isEmpty()) {
			for (User friend : friends) {
				for (User member : groupMembers) {
					if (friend.getUsername().equals(member.getUsername())) {
						friends.remove(friends.indexOf(member));
					}
				}
			}
		}
		return friends;
	}

	/**
	 * Muestra todos los usuarios ordenados para el ranking
	 * 
	 * @return
	 */
	public List<User> getAllUsers() {
		List<User> listaUsuarios = userRepo.findAllUsers();

		for (int i = 0; i < listaUsuarios.size(); i++) {
			listaUsuarios.get(i).setUserInitSession();
		}

		List<User> listaUsuariosOrdenada = new ArrayList<>();
		Collections.sort(listaUsuarios, new OrdenarPorNumero());
		for (User e : listaUsuarios) {
			listaUsuariosOrdenada.add(e);
		}

		return listaUsuariosOrdenada;
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
	 * 
	 * @param result
	 */
	public void borrarUsuario(Long idDelete) {
		//Comprueba que el usuario a borrar existe
		
		User user = userRepo.findById(idDelete).orElse(null);
		try {
		
			List<CommentCommunity> comentarios = commentsCommutinyRepo.findAll();
			for (int i = 0; i < comentarios.size(); i++) {
				if(comentarios.get(i).getUser().getId().equals(user.getId())) {
					commentsCommutinyRepo.deleteById(comentarios.get(i).getId());
				}
			}
			
			List<CommentsGroup> comentariosGrupo = CommentsGroupRepo.findAll();
			for (int i = 0; i < comentariosGrupo.size(); i++) {
				if(comentariosGrupo.get(i).getUser().getId().equals(user.getId())) {
					CommentsGroupRepo.deleteById(comentariosGrupo.get(i).getId());
				}
			}
			
			List<MeetUp> meetups = meetUpRepo.findAllMeetUps();
			for (int i = 0; i < meetups.size(); i++) {
				for (int j = 0; j < meetups.get(i).getAssistantsList().size(); j++) {
					if(meetups.get(i).getAssistantsList().get(j).getId().equals(user.getId())) {
						meetUpRepo.deleteById(meetups.get(i).getAssistantsList().get(j).getId());
					}
				}
					
			}
			
			
		}catch (Exception e) {
			throw new UserNotFoundException();
		}

		userRepo.delete(user);
	}

	public List<User> findUsers() {
		return userRepo.findUsers();
	}

	/**
	 * Consigue la propiedad message del usuario para poder indicar que se le ha
	 * mandado un mensaje y el usuario ya lo ha leído (que será cuando se marque a
	 * false). Se guardan los cambios en la bb.dd.
	 */
	public User setPropertyMessageToFalse(User user) {
		userRepo.findById(user.getId()).get().setMessage(false);
		return userRepo.save(user);
	}

	/**
	 * Establece la url de la imagen de perfil del usuario
	 * 
	 * @param user
	 * @param url
	 * @return usuario con el campo de su imagen editado
	 */
	public User setUrlImage(User user, String url) {
		user.setImageUrl(url);
		return userRepo.save(user);
	}

	/**
	 * Se agrega mutuamente a los usuarios como amigos. Se guardan en bbdd
	 * 
	 * @param result
	 * @param userRecibido
	 * @return usuario actualizado con su amigo añadido
	 */
	public User addfriend(User result, User userRecibido) {
		if (userRepo.existsById(result.getId())) {
			User user = userRepo.findById(result.getId()).get();
			User user2 = userRepo.findById(userRecibido.getId()).get();

			user.addFriend(user2);
			user2.addFriend(user);

			userRepo.save(user2);
			userRepo.save(user);

			return user;
		} else {
			return null;
		}
	}

	public List<User> getAllFriends(User result) {
		if (userRepo.existsById(result.getId())) {
			// User user = userRepo.findById(result.getId()).orElse(null);
			List<Long> idFriendList = userRepo.searchFriends(result.getId());
			List<User> friendList = new ArrayList<>();
			for (int i = 0; i < idFriendList.size(); i++) {
				User userEncontrado = userRepo.findById(idFriendList.get(i)).orElse(null);
				if (userEncontrado != null) {
					friendList.add(userEncontrado);
				}
			}
			return friendList;

		} else {
			return null;
		}
	}

	public User getUserPassword(String password, User result) {
		try {
			User user = userRepo.findById(result.getId()).get();
			
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					result.getEmail(), password);

			authManager.authenticate(authInputToken);
			
			user.setPassword(passwordEncoder.encode(password));
			return userRepo.save(user);
		} catch (Exception e) {
			throw new PasswordException();
		}
		
		
		
	}

	public User changePass(User user, String password) {
		
		if (userRepo.existsById(user.getId())) {
			User usuario = userRepo.findById(user.getId()).get();

			usuario.setPassword(passwordEncoder.encode(password));

			return userRepo.save(usuario);
		} else {
			return null;
		}
	}

}
