package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.error.FileNotFoundException;
import com.example.demo.model.File;
import com.example.demo.model.User;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepo;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepo;

	@Autowired
	private UserRepo userRepo;

	/**
	 * Transforma la imagen que recibe, que es un objeto multipartfile en fileDB
	 * para poder almacenarlo
	 * 
	 * @param file
	 * @return FileDB guardado
	 * @throws IOException
	 */
	public File addFile(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File file1 = new File(fileName, file.getContentType(), file.getBytes());
		return fileRepo.save(file1);
	}

	/**
	 * Consigue la imagen guardada del usuario a través del id de la foto que posee
	 * guardada en la bbdd el usuario
	 * 
	 * @param user
	 * @return FileDB del usuario
	 */
	public File getFileByUserFileId(User user) {
		try {
			String fileIdFromUser = user.getFile().getId();
			return fileRepo.getFileFromUser(fileIdFromUser);
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Elimina una imagen del usuario usando el id guardado en el usuario de la
	 * misma
	 * 
	 * @param user
	 */
	public void deleteFileFromUser(User user) {
		try {
			String fileIdFromUser = user.getFile().getId();
			fileRepo.deleteById(fileIdFromUser);
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Modificación de imagen. Usada cuando el usuario desea subir una nueva imagen
	 * para sustituir su anterior imagen de perfil
	 * 
	 * @param user
	 * @param file
	 * @return FileDB sustituida del usuario
	 * @throws IOException
	 */
	public File modifyFileFromUser(User user, MultipartFile file) throws IOException {
		try {
			// Consigo el archivo que tengo que cambiar --> sustituir esta imagen
			File fileAModificar = getFileByUserFileId(user);
			// Seteo la imagen
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			fileAModificar.setName(fileName);
			fileAModificar.setType(file.getContentType());
			fileAModificar.setData(file.getBytes());
			return fileRepo.save(fileAModificar);
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Encuentra la imagen en la bbdd y se la asigna al usuario correspondiente
	 * @param fileName
	 * @param user
	 * @return la imagen del usuario
	 */
	public File setFileToUser(String fileName, User user) {
		// Encuentro el archivo en la bbdd
		File file;
		try {
			file = fileRepo.findByName(fileName);
		} catch (Exception e) {
			throw new FileNotFoundException();
		}

		// Se la asigno al usuario
		user.setFile(file);
		userRepo.save(user);
		return file;

	}

}
