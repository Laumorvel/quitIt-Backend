package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.CommentsGroup;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.CommentsGroupService;

@RestController
public class CommentsGroupController {


	@Autowired
	private CommentsGroupService commentsGroupService;

	@Autowired
	private UserRepo userRepo;
	
	/**
	 * Método para comprobar que el usuario que contiene el token es correcto.
	 * @return usuario en contrado
	 * @throws exception en caso de no encontrar al usuario
	 */
	private User checkUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userRepo.findByEmail(email) == null) {
			throw new UserNotFoundException();
		}
		return userRepo.findByEmail(email);
	}
	
	/**
	 * Consigue los comentarios de grupo sin diferenciar grupo
	 * @return todos los comentarios de grupo
	 */
	@GetMapping("/commentsGroup")
	public List<CommentsGroup> getComments() {
		checkUser();
		return commentsGroupService.getComments();
	}
	
	/**
	 * Consigue los comentarios de un grupo
	 * @param idGroup
	 * @return lista de comentarios de un grupo
	 */
	@GetMapping("/group/{idGroup}/commentsGroup")
	public List<CommentsGroup> getCommentsFromGroup(@PathVariable Long idGroup){
		checkUser();
		return commentsGroupService.getGroupComments(idGroup);
	}
	
	/**
	 * Publica un nuevo comentario en la bbdd.
	 * Relacionado con un grupo concreto
	 * @param idGroup
	 * @param comment
	 * @return comentario de grupo publicado
	 */
	@PostMapping("/group/{idGroup}/commentsGroup")
	public CommentsGroup postNewComment(@PathVariable Long idGroup, @RequestBody CommentsGroup comment) {
		return commentsGroupService.addCommentGroup(checkUser(), comment, idGroup);
	}
	
	

}
