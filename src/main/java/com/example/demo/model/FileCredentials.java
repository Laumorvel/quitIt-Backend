package com.example.demo.model;

/**
 * Se ha creado esta clase para poder acceder al nombre del archivo que se envia
 * en el cuerpo de la petición, con lo que poder encontrarlo en la bbdd
 * 
 * @author laura
 *
 */
public class FileCredentials {

	private String fileName;

	public FileCredentials() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
