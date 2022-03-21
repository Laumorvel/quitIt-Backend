package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Incidence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subject;
	private String text;
	@ManyToOne
	private User user;
	private State state;
	
	
	/**
	 * Constructor vacio
	 */
	public Incidence() {}

	/**
	 * Constructor con todos los atributos de las incidencias
	 * @param subject
	 * @param text
	 * @param user
	 * @param state
	 */
	public Incidence(String subject, String text, User user, State state) {
		this.subject = subject;
		this.text = text;
		this.user = user;
		this.state = state;
	}


	/**
	 * Getters y setter de indicendia
	 * @return
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	/**
	 * HashCode y Equals de la id de la incidencia
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
		Incidence other = (Incidence) obj;
		return Objects.equals(id, other.id);
	}

	
	/**
	 * ToString de los atributos de incidencia
	 */
	@Override
	public String toString() {
		return "Incidence [id=" + id + ", subject=" + subject + ", text=" + text + ", user=" + user + ", state=" + state
				+ "]";
	}
	
	
	

}
