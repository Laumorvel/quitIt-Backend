package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CommentsCommunity;
import com.example.demo.model.CommentsGroup;

public interface CommentsCommunityRepo  extends JpaRepository<CommentsCommunity, Long>{

}
