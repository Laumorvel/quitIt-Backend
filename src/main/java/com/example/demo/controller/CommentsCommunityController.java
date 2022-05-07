package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.CommentNotExist;
import com.example.demo.error.IncidenceNotExist;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.CommentsCommunityService;
import com.example.demo.service.IncidenceService;

@RestController
public class CommentsCommunityController {
	
	@Autowired
	private CommentsCommunityService commentsCommunityService;
	
	@Autowired
	private IncidenceService incidenceService;
	
	@Autowired
	private UserRepo userRepo;

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
	public CommentCommunity deleteCommentsCommunity(@PathVariable Long idC) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = userRepo.findByEmail(email).getId();

		if (id == null) {
			throw new UserNotFoundException();
		} else {
			CommentCommunity result = commentsCommunityService.delete(idC);

			if (result == null) {
				throw new CommentNotExist(id);
			} else {
				return result;
			}
		}

	}
}
