package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.File;

public interface FileRepository extends JpaRepository<File, String>{
	
	/**
	 * Consigue la foto del usuario
	 * @return FileDB 
	 */
	@Query(value="SELECT * FROM filedb f WHERE id=?1", nativeQuery = true)
	File getFileFromUser(String fileId);

}
