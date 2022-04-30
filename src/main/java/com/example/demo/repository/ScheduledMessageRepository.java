package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.ScheduledMessage;

public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, Long> {
	
	/**
	 * Consigue el id del último mensaje enviado
	 * @return
	 */
	@Query(value="SELECT id FROM scheduled_message WHERE sent = true", nativeQuery = true)
	Long getLastMessageSentId();
	
	/**
	 * Consigue todos los mensajes del repositorio
	 * @return mensajes
	 */
	@Query(value="SELECT * FROM scheduled_message WHERE sent = true", nativeQuery = true)
	ScheduledMessage getLastMessageSent();

}
