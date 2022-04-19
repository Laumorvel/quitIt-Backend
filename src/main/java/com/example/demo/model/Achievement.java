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
	private String porcentaje;

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

	public Achievement(String name, String text, String img, String porcentaje) {
		this.name = name;
		this.text = text;
		this.img = img;
		this.porcentaje = porcentaje;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
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
