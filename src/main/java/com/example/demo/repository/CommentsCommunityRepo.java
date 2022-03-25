package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CommentsCommunity;

public interface CommentsCommunityRepo  extends JpaRepository<CommentsCommunity, Long>{

	@Query(value="select * from comments_community", nativeQuery = true) 
	public List<CommentsCommunity> findAllCommentsComunity();
	
}
