package org.jesuitasrioja.Reservas.modelo.reserva;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

	private Integer identificador;
	private String nombre;
	private String apellidos;
	private String fecha;
	private String hora;
	private Integer servicioIdentificador;
	private String servicioNombre;
	private Double servicioPrecio;
	private String telefono;
	
}
