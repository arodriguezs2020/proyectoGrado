package org.jesuitasrioja.Reservas.modelo.servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTO {
	private Integer identificador;
	private String nombre;
	private String precio;
}
