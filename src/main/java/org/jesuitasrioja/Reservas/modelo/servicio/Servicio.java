package org.jesuitasrioja.Reservas.modelo.servicio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Include;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Servicio")
public class Servicio implements Serializable{

	@Id @Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;
	private String nombre;
	private Integer precio;
}
