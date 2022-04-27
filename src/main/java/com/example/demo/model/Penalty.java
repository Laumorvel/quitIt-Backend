package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Penalty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String text;
	private String img;
	private Integer objective;
	private String type;
	
	/**
	 * Constructor vacio.
	 */
	public Penalty() {}
	
	/**
	 * Constructor con todos los atributos de penalizacion
	 * @param name
	 * @param text
	 */
	public Penalty(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	public Penalty(String name, String text, String img, Integer objective, String type) {
		this.name = name;
		this.text = text;
		this.img = img;
		this.objective = objective;
		this.type = type;
	}

	/**
	 * Getters y setters de penalizacion
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getObjective() {
		return objective;
	}

	public void setObjective(Integer objective) {
		this.objective = objective;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * HashCode y Equals de la id de penalizacion
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
		Penalty other = (Penalty) obj;
		return Objects.equals(id, other.id);
	}
	
	/**
	 * ToString con todos los atributos de penalizacion
	 */
	@Override
	public String toString() {
		return "Penalty [id=" + id + ", name=" + name + ", text=" + text + "]";
	}
	
	
	
}
