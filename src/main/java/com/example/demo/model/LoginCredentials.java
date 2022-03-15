package com.example.demo.model;

import java.util.Objects;


/**
 * Clase que comprueba las credenciales del login
 * @author adela y laura
 *
 */
public class LoginCredentials {

	/**
	 * ATRIBUTOS
	 */
    private String email;
    private String password;
    
    /**
	 * Constructor vacio
	 */
	public LoginCredentials() {}
	
	/**
	 * Constructor con todos los atributos de loginCredentials
	 */
	public LoginCredentials(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	/**
	 * Getters y setters
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	/**
	 * HashCode y Equals de la id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginCredentials other = (LoginCredentials) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password);
	}

	/**
	 * ToString de todos los atributos de loginCredentials
	 */
	@Override
	public String toString() {
		return "LoginCredentials [email=" + email + ", password=" + password + "]";
	}
    
    
    

}