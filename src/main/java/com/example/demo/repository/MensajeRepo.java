package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Message;

public interface MensajeRepo extends JpaRepository<Message, Long> {


	
}
