package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CommentCommunity;

public interface CommentsCommunityRepo  extends JpaRepository<CommentCommunity, Long>{
	
	/**
	 * Query que devuelve una lista de todos los comentarios de la comunidad de la base de datos
	 * @return
	 */
	@Query(value="SELECT * FROM comments_community", nativeQuery = true)
	List<CommentCommunity>getComentarios();


}
