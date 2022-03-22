package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String toUser;
	private String subject;
	private String text;
	private String fromUser;

	/**
	 * Constructor vacio
	 */
	public Message() {
	}

	/**
	 * Constructor con todos los atributos
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @param from
	 */
	public Message(String to, String subject, String text, String from) {
		this.toUser = to;
		this.subject = subject;
		this.text = text;
		this.fromUser = from;
	}

	/**
	 * Getters y setters del mensaje
	 * 
	 * @return
	 */

	/**
	 * HashCode y Equals de la id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", toUser=" + toUser + ", subject=" + subject + ", text=" + text + ", fromUser="
				+ fromUser + "]";
	}

	/**
	 * ToString con todos los atributos
	 */

}

