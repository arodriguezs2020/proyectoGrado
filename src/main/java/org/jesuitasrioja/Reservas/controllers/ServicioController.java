package org.jesuitasrioja.Reservas.controllers;

import java.util.List;
import java.util.Optional;

import org.jesuitasrioja.Reservas.modelo.reserva.Reserva;
import org.jesuitasrioja.Reservas.modelo.servicio.Servicio;
import org.jesuitasrioja.Reservas.persistencia.services.ReservaService;
import org.jesuitasrioja.Reservas.persistencia.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class ServicioController {

	@Autowired
	private ServicioService ss;
	
	@Autowired
	private ReservaService rs;

	/*
	 * 
	 * GET servicios
	 * 
	 */

	@ApiOperation(value = "Obtener todos los servicios paginados", notes = "Con este metodo conseguimos mandar todos los servicios de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/servicios")
	public ResponseEntity<?> allServicios(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		List<Servicio> servicios = ss.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(servicios);
	}

	/*
	 * 
	 * GET servicio/{idServicio}
	 * 
	 */

	@ApiOperation(value = "Obtener un servicio por identificador", notes = "Con este metodo conseguimos recoger la información de un servicio específico.")
	@GetMapping("/servicio/{nombre}")
	public ResponseEntity<Servicio> getServicio(@PathVariable String nombre) {

		Optional<Servicio> servicioOptional = ss.findById(nombre);
		if (servicioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(servicioOptional.get());
		} else {
			throw new ServicioNoEncontradoException(nombre);
		}
	}

	/*
	 * 
	 * POST servicio
	 * 
	 */

	@ApiOperation(value = "Añadir un servicio", notes = "Con este metodo conseguimos añadir un servicio.")
	@PostMapping("/servicio")
	public String postServicio(@RequestBody Servicio nuevoServicio) {
		return ss.save(nuevoServicio).toString();
	}

	/*
	 * 
	 * PUT servicio
	 * 
	 */

	@ApiOperation(value = "Modificar un servicio", notes = "Con este metodo conseguimos modificar un Servicio.")
	@PutMapping("/servicio")
	public String putServicio(@RequestBody Servicio editadoServicio) {
		ss.edit(editadoServicio);
		
		List<Reserva> reservas = rs.findAll();
		
		for (Reserva reserva : reservas) {
			Servicio servicio = reserva.getServicio();
			if(servicio.getNombre().equals(editadoServicio.getNombre())) {
				servicio.setPrecio(editadoServicio.getPrecio());
			}
		}
		
		
		return "OK";
	}

	/*
	 * 
	 * DELETE servicio/{idServicio}
	 * 
	 **/

	@ApiOperation(value = "Borrar un servicio", notes = "Con este metodo conseguimos borrar un Servicio por identificador. De esta forma conseguiremos borrar un Servicio específico.")
	@DeleteMapping("/servicio/{nombre}")
	public String deleteServicio(@PathVariable String nombre) {
		ss.deleteById(nombre);
		return "OK";
	}
}
