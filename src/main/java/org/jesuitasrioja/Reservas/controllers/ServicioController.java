package org.jesuitasrioja.Reservas.controllers;

import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.Reservas.modelo.servicio.Servicio;
import org.jesuitasrioja.Reservas.modelo.servicio.ServicioDTO;
import org.jesuitasrioja.Reservas.modelo.servicio.ServicioDTOConverter;
import org.jesuitasrioja.Reservas.persistencia.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	ServicioDTOConverter servicioDTOConverter;

	/*
	 * 
	 * GET servicios
	 * 
	 * */
	
	@ApiOperation(value = "Obtener todos los servicios paginados",
			 notes = "Con este metodo conseguimos mandar todos los servicios de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/servicios")
	public ResponseEntity<?> allServicios(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Servicio> pagina = ss.findAll(pageable);
		
		// transformar elementos de la pagina a DTO
				Page<ServicioDTO> paginaDTO = pagina.map(new Function<Servicio, ServicioDTO>() {
					@Override
					public ServicioDTO apply(Servicio s) {
						return servicioDTOConverter.convertServicioToServicioDTO(s);
					}
				});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}
	
	/*
	 * 
	 * GET servicio/{idServicio}
	 * 
	 * */
	
	@ApiOperation(value = "Obtener un servicio por identificador",
			 notes = "Con este metodo conseguimos recoger la información de un servicio específico.")
	@GetMapping("/servicio/{id}")
	public ResponseEntity<Servicio> getServicio(@PathVariable Integer id) {

		Optional<Servicio> servicioOptional = ss.findById(id);
		if (servicioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(servicioOptional.get());
		} else {
			throw new ServicioNoEncontradoException(id);
		}
	}
	
	/*
	 * 
	 * POST servicio
	 * 
	 * */
	
	@ApiOperation(value = "Añadir un servicio",
			 notes = "Con este metodo conseguimos añadir un servicio.")
	@PostMapping("/servicio")
	public String postServicio(@RequestBody Servicio nuevoServicio) {
		return ss.save(nuevoServicio).toString();
	}
	
	/*
	 * 
	 * PUT servicio
	 * 
	 * */
	
	@ApiOperation(value = "Modificar un servicio",
			 notes = "Con este metodo conseguimos modificar un Servicio.")
	@PutMapping("/servicio")
	public String putServicio(@RequestBody Servicio editadoServicio) {
		return ss.edit(editadoServicio).toString();
	}
	
	/*
	 * 
	 * DELETE servicio/{idServicio}
	 * 
	 **/
	
	@ApiOperation(value = "Borrar un servicio",
			 notes = "Con este metodo conseguimos borrar un Servicio por identificador. De esta forma conseguiremos borrar un Servicio específico.")
	@DeleteMapping("/servicio/{id}")
	public String deleteServicio(@PathVariable Integer id) {
		ss.deleteById(id);
		return "OK";
	}
}
