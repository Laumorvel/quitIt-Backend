package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Advice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String text;
	
	
	/**
	 * Constructor vacio
	 */
	public Advice() {}
	
	/**
	 * Constructor con todos los atributos de consejos
	 * @param title
	 * @param text
	 */
	public Advice(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}
	
	/**
	 * Getters y setters con todos los atributos de consejos
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * HashCode y Equals de la id de consejos
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
		Advice other = (Advice) obj;
		return Objects.equals(id, other.id);
	}
	
	/**
	 * ToString de  todos los atributos de consejo
	 */
	@Override
	public String toString() {
		return "Advice [id=" + id + ", title=" + title + ", text=" + text + "]";
	}
	
	

}
