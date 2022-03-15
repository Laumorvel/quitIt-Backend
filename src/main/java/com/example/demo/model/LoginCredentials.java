package com.example.demo.model;

import java.util.Objects;


/**
 * CLASE QUE COMPRUEBA LAS CREDENCIALES AL HACER EL LOGIN
 * @author adela
 *
 */
public class LoginCredentials {

	/**
	 * ATRIBUTOS
	 */
    private String email;
    private String password;
    
    /**
	 * CONSTRUCTOR VACIO
	 */
	public LoginCredentials() {
		super();
	}
	
	/**
	 * CONSTRUCTOR CON EMAIL Y PASSWORD
	 */
	public LoginCredentials(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	/**
	 * GETTER Y SETTERS DE LOS ATRIBUTOS
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
	 * HASHCODE Y EQUALS DE LA ID
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
	 * TOSTRING DE TODOS LOS ATRIBUTOS DE LOGINCREDENTIALS
	 */
	@Override
	public String toString() {
		return "LoginCredentials [email=" + email + ", password=" + password + "]";
	}
    
    
    

}