package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CommentCommunity;
import com.example.demo.model.CommentsGroup;

public interface CommentsGroupRepo extends JpaRepository<CommentsGroup, Long>{
	
	/**
	 * Selecciona solo los comentarios de un grupo en concreto
	 * @param idGroup
	 * @return comentarios de un grupo
	 */
	@Query(value="SELECT * FROM comments_group WHERE id_group = ?1", nativeQuery = true)
	List<CommentsGroup>getGroupComments(Long idGroup);

	/**
	 * Query que devuelve una lista de todos los comentarios del grupo de la base de datos
	 * @return
	 */
	@Query(value="SELECT * FROM comments_group", nativeQuery = true)
	List<CommentsGroup>getComentarios();
	
}
