package org.jesuitasrioja.Reservas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ServicioNoEncontradoException extends RuntimeException{
	public ServicioNoEncontradoException(String nombreServicio) {
		super("servicio with " + nombreServicio + " can not be retrieved");
	}
}
