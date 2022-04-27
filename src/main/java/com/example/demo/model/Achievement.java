package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * El usuario obtendra logros según los días que lleve sin fumar
 * 
 * @author adela y laura
 *
 */

@Entity
public class Achievement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String text;
	private String img;
	private Integer objective;
	private String type;// money, cigarettes, daysInARow, days

	/**
	 * Constructor vacio.
	 */
	public Achievement() {
	}

	/**
	 * Constructor con todos los atributos de logro
	 * 
	 * @param name
	 * @param text
	 */
	public Achievement(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public Achievement(String name, String text, String img) {
		this.name = name;
		this.text = text;
		this.img = img;
	}

	public Achievement(String name, String text, String img, Integer objective, String type) {
		this.name = name;
		this.text = text;
		this.img = img;
		this.objective = objective;
		this.type = type;
	}

	/**
	 * Getters y setters de logro
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	/**
	 * HashCode y Equals de la id de logro
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
		Achievement other = (Achievement) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * ToString con todos los atributos de logro
	 */
	@Override
	public String toString() {
		return "Achievement [id=" + id + ", name=" + name + ", text=" + text + "]";
	}

}
