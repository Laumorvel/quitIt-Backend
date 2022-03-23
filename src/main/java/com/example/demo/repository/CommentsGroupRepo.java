package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CommentsGroup;

public interface CommentsGroupRepo extends JpaRepository<CommentsGroup, Long>{

}
