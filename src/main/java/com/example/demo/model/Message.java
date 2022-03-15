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
	private String to;
	private String subject;
	private String text;
	private String from;
	
	/**
	 * Constructor vacio
	 */
	public Message() {}


	/**
	 * Constructor con todos los atributos
	 * @param to
	 * @param subject
	 * @param text
	 * @param from
	 */
	public Message(String to, String subject, String text, String from) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.from = from;
	}


	/**
	 * Getters y setters del mensaje
	 * @return
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
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


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	/**
	 * HashCode y Equals de la id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
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

	
	/**
	 * ToString con todos los atributos
	 */
	@Override
	public String toString() {
		return "Message [id=" + id + ", to=" + to + ", subject=" + subject + ", text=" + text + ", from=" + from + "]";
	}
	
	
	
	
	
	

}
