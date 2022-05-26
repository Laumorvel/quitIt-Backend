package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CommentsGroup;
import com.example.demo.model.User;
import com.example.demo.repository.CommentsGroupRepo;
import com.example.demo.repository.UserRepo;

@Service
public class CommentsGroupService {

	@Autowired
	CommentsGroupRepo commentsGroupRepo;

	@Autowired
	UserRepo userRepo;

	/**
	 * Consigue todos los comentarios de grupo guardados
	 * 
	 * @return todos los comentarios de grupo de todos los grupos
	 */
	public List<CommentsGroup> getComments() {
		return commentsGroupRepo.findAll();
	}
	
	/**
	 * Consigue todos los comentarios de un grupo en específico
	 * @param idGroup
	 * @return comentarios de un grupo
	 */
	public List<CommentsGroup> getGroupComments(Long idGroup){
		return commentsGroupRepo.getGroupComments(idGroup);
	}

	/**
	 * Crea un nuevo comentario de grupo indicando el grupo
	 * 
	 * @param user
	 * @param datos
	 * @param idGroup
	 * @return comentario creado
	 */
	public CommentsGroup addCommentGroup(User user, CommentsGroup datos, Long idGroup) {

		CommentsGroup comment = new CommentsGroup();

		comment.setDate(LocalDate.now());
		comment.setText(datos.getText());
		comment.setUser(user);
		comment.setIdGroup(idGroup);

		commentsGroupRepo.save(comment);
		userRepo.save(user);

		return comment;
	}

}
