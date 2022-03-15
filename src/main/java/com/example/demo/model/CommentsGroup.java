package com.example.demo.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentsGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	private User user;
	private LocalDate date;
	
	/**
	 * Constructor vacio.
	 */
	public CommentsGroup() {}

	/**
	 * Constructor con todos los atributos de Comentario
	 * @param text
	 * @param user
	 * @param date
	 */
	public CommentsGroup(String text, User user, LocalDate date) {
		super();
		this.text = text;
		this.user = user;
		this.date = date;
	}

	/**
	 * Getters y setters de los comentarios
	 * @return
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * HashCode y Equals de la id de los comentarios
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
		CommentsGroup other = (CommentsGroup) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * ToString de los atributos de los Comentarios
	 */
	@Override
	public String toString() {
		return "ComentsCommunity [id=" + id + ", text=" + text + ", user=" + user + ", date=" + date + "]";
	}
	
	
}
