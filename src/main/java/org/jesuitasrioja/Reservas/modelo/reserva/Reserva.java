package org.jesuitasrioja.Reservas.modelo.reserva;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jesuitasrioja.Reservas.modelo.servicio.Servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reserva")
public class Reserva implements Serializable{

	@Id
	@Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;
	private String nombre;
	private String apellidos;
	private String fecha;
	private String hora;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "servicio")
	private Servicio servicio;
	
	private String telefono;
	
}
