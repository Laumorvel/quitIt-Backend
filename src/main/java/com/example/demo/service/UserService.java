package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.error.AchievementAlreadyAddedException;
import com.example.demo.error.AchievementNotAddedException;
import com.example.demo.error.AchievementNotFoundException;
import com.example.demo.error.PenaltyAlreadyAddedException;
import com.example.demo.error.PenaltyNotFoundException;
import com.example.demo.error.PasswordException;
import com.example.demo.error.UserNotFoundException;

import com.example.demo.model.Achievement;

import com.example.demo.model.CommentCommunity;
import com.example.demo.model.CommentsGroup;
import com.example.demo.model.Group;
import com.example.demo.model.GroupMember;
import com.example.demo.model.LoginCredentials;
import com.example.demo.model.MeetUp;

import com.example.demo.model.OrdenarPorNumero;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;

import com.example.demo.repository.AchievementRepo;
import com.example.demo.repository.PenaltyRepo;

import com.example.demo.repository.CommentsCommunityRepo;
import com.example.demo.repository.CommentsGroupRepo;
import com.example.demo.repository.GroupMemberRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MeetUpRepo;

import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired UserRepo userRepo;
	
	@Autowired CommentsCommunityRepo commentsCommutinyRepo;
	
	@Autowired CommentsGroupRepo CommentsGroupRepo;
	
	@Autowired MeetUpRepo meetUpRepo;

	@Autowired
	AchievementRepo achievementRepo;
	
	@Autowired PenaltyRepo penaltyRepo;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
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
	 * Añade un achievement al usuario.
	 * Comprueba que exista y que el usuario no lo tenga ya añadido
	 * @param achievement
	 * @param user
	 * @return usuario modificado
	 */
	public User addAchievementToUser(Achievement achievement, User user) {
		try {
			achievementRepo.getById(achievement.getId());
		} catch (Exception e) {
			throw new AchievementNotFoundException();
		}
		for (Achievement a : user.getAchievementList()) {
			if (a.equals(achievement)) {
				throw new AchievementAlreadyAddedException();
			}
		}
		user.getAchievementList().add(achievement);
		return userRepo.save(user);

	}
	
	/**
	 * Elimina un achievement del usuario.
	 * Comprueba que exista y el usuario lo tenga incluido en su lista.
	 * @param idAchievement
	 * @param user
	 */
	public void deleteAchievementOfUser(Long idAchievement, User user) {
		Achievement achievement;
		try {
			achievement = achievementRepo.getById(idAchievement);
		} catch (Exception e) {
			throw new AchievementNotFoundException();
		}
		Boolean found = false;
		for (Achievement a : user.getAchievementList()) {
			if (a.equals(achievement)) {
				found = true;
			}
		}
		if(!found) {
			throw new AchievementNotAddedException();
		}
		user.getAchievementList().remove(achievement);
		userRepo.save(user);
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
		Boolean found = false;
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
			
			List<Group> groupList = groupRepository.getGroupsFromUser(user.getId());
			for (Group g : groupList) {
				
				boolean existe=false;
				Iterator<GroupMember> groupMember = g.getGroupMembers().iterator();

				while (existe == false && groupMember.hasNext() ){
					GroupMember e = groupMember.next();
					if(e.getUser().equals(user)) {
						g.getGroupMembers().remove(g.getGroupMembers().indexOf(e));
						groupRepository.save(g);
						groupMemberRepository.delete(e);
						existe=true;
					}
				}
				
				
				
				
//				for (GroupMember groupMember : g.getGroupMembers()) {
//					if (user.equals(groupMember.getUser() )) {
//						g.getGroupMembers().remove(g.getGroupMembers().indexOf(groupMember));
//						groupRepository.save(g);
//						groupMemberRepository.delete(groupMember);
//						found=true;
//						
//					}
//				}
			}
			
			
			
			
//			for (int i = 0; i < group.size(); i++) {
//				for (int j = 0; j < group.get(i).getGroupMembers().size(); j++) {
//					if(group.get(i).getGroupMembers().get(j).getUser().getUsername().equals(user.getUsername())) {
//						group.get(i).deleteMember(group.get(i).getGroupMembers().get(j));
//						groupRepository.save(group.get(i));
//						groupMemberRepository.deleteById(group.get(i).getGroupMembers().get(j).getUser().getId());
//						
//					}
//				}
//			}
			
//			List<GroupMember> groupMember = groupMemberRepository.findAllGroupMembers();
//			for (int i = 0; i < groupMember.size(); i++) {
//				if(groupMember.get(i).getUser().getId().equals(user.getId())) {
//					groupMemberRepository.deleteById(groupMember.get(i).getId());
//				}
//			}
			
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
	
	/**
	 * Elimina una penalización del usuario
	 * @param user
	 * @param id
	 * @return usuario modificado
	 */
	public User addPenaltyToUser(User user, Long id) {
		Penalty penalty;
		if(penaltyRepo.getById(id) != null) {
			penalty = penaltyRepo.getById(id);
		}else {
			throw new PenaltyNotFoundException();
		}
		Boolean found = false;
		for (Penalty p : user.getPenalties()) {
			if (p.equals(penalty)) {
				found = true;
			}
		}
		if(found) {
			throw new PenaltyAlreadyAddedException();
		}
		user.getPenalties().remove(penalty);
		return userRepo.save(user);
	}

	public User getUserPassword(String password, User user) {
		try {
			LoginCredentials loggedInUser = new LoginCredentials(user.getEmail(), password);
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					loggedInUser.getEmail(), loggedInUser.getPassword());

			authManager.authenticate(authInputToken);
			
			//user.setPassword(passwordEncoder.encode(password));
			//return userRepo.save(user);
			return user;
		} catch (Exception e) {
			throw new PasswordException();
		}
		
		
		
	}

	public User changePass(User user, String password) {
		if (userRepo.existsById(user.getId())) {
			user.setPassword(passwordEncoder.encode(password));

			return userRepo.saveAndFlush(user);
		} else {
			return null;
		}
	}

}
