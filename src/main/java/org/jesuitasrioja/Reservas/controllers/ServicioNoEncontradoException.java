package org.jesuitasrioja.Reservas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ServicioNoEncontradoException extends RuntimeException{
	public ServicioNoEncontradoException(Integer idServicio) {
		super("servicio with " + idServicio + " can not be retrieved");
	}
}
