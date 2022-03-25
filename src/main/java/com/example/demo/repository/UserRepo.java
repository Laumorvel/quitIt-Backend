package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.User;



/**
 * INTERFAZ QUE NOS PERMITE COMUNICARNOS CON EL REPOSITORIO DEL USUARIO
 * @author adela
 *
 */
public interface UserRepo extends JpaRepository<User, Long> {
  
	/**
	 * METODO QUE NOS ENCUENTRA EL EMAIL DEL USUARIO
	 * @param email
	 * @return
	 */
	public User findByEmail(String email);

	public User findByUsername(String username);

	
	@Query(value="select * from user", nativeQuery = true) 
	public List<User> findAllUsers();



	
}