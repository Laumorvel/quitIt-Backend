package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.error.AlreadyExistingFileException;
import com.example.demo.error.ApiError;
import com.example.demo.error.FileNotFoundException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.fileMessage.MessageFile;
import com.example.demo.model.File;
import com.example.demo.model.FileCredentials;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.FileService;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserRepo userRepo;

	/**
	 * Consigue una imagen concreta
	 * 
	 * @return la imagen buscada
	 */
	@GetMapping("/file")
	public File getFileFromUser() {
		// Se consigue al usuario
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}

		return fileService.getFileByUserFileId(user);
	}

	/**
	 * Método para añadir una nueva imagen del usuario
	 * 
	 * @param file
	 * @return mensaje de error o éxito de la subida de la imagen
	 * @throws IOException
	 */
	@PostMapping("/file")
	public ResponseEntity<MessageFile> addFile(@RequestParam("file") MultipartFile file) throws IOException {

		String msg;
		// Se devuelve un mensaje indicando si se pudo o no subir la imagen
		try {
			fileService.addFile(file);
			msg = "Your file was successfully uploaded";
			return ResponseEntity.status(HttpStatus.OK).body(new MessageFile(msg));
		} catch (Exception e) {
			msg = "Failed upload. Your file encountered an error while trying to upload it";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageFile(msg));
		}

	}

	@PostMapping("/file/user")
	public File setAndGetFileFromUser(@RequestBody FileCredentials fileName) {
		// Se consigue al usuario
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}

		return fileService.setFileToUser(fileName.getFileName(), user);
	}

	/**
	 * Modifica la imagen del usuario sustituyéndola por otra que se pasa por
	 * requestParam
	 * 
	 * @param id
	 * @param file
	 * @return mensaje de éxito o error de la subida de imagen
	 * @throws IOException
	 */
	@PutMapping("/file/{id}")
	public File modifyFileFromUser(@PathVariable String id, @RequestParam("file") MultipartFile file)
			throws IOException {
		User user;
		try {
			String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userRepo.findByEmail(email);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		return fileService.modifyFileFromUser(user, file);
	}

	// EXCEPCIONES------------------------------------------------------------

	/**
	 * Se gestiona la excepción específica si el archivo no se encuentra
	 * 
	 * @param ex
	 * @return traza controlada de la excepción
	 */
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiError> handleFileNotFound(FileNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	/**
	 * Gestión de la excepción UserNotFoundException cuando el usuario no se
	 * encuentra
	 * 
	 * @param ex
	 * @return json con los datos de la excepción y la traza controlada
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> handleFileNotFound(UserNotFoundException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	/**
	 * Controla la excepción de un archivo que se intenta publicar en un usuario que
	 * ya posee uno
	 * 
	 * @param ex
	 * @return json con los datos de la excepción
	 */
	@ExceptionHandler(AlreadyExistingFileException.class)
	public ResponseEntity<ApiError> handleFileNotFound(AlreadyExistingFileException ex) {
		ApiError apiError = new ApiError();
		apiError.setEstado(HttpStatus.NOT_FOUND);
		apiError.setFecha(LocalDateTime.now());
		apiError.setMensaje(ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
}
