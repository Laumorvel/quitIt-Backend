package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

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

	/**
	 * Query para conseguir a un usuario a través del id de su imagen
	 * @param fileId
	 * @return user
	 */
	@Query(value="SELECT * FROM user WHERE file_id=?1", nativeQuery = true)
	public User getUserFromFileId(String fileId);

	@Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
	public User findUserByUsername(String busqueda);


	
}