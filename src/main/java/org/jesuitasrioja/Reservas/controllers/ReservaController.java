package org.jesuitasrioja.Reservas.controllers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jesuitasrioja.Reservas.modelo.reserva.Reserva;
import org.jesuitasrioja.Reservas.modelo.reserva.ReservaDTO;
import org.jesuitasrioja.Reservas.modelo.reserva.ReservaDTOConverter;
import org.jesuitasrioja.Reservas.modelo.servicio.Servicio;
import org.jesuitasrioja.Reservas.persistencia.services.ReservaService;
import org.jesuitasrioja.Reservas.persistencia.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ReservaController {

	@Autowired
	private ReservaService rs;

	@Autowired
	private ServicioService ss;

	@Autowired
	ReservaDTOConverter reservaDTOConverter;

	/*
	 * 
	 * GET reservas:
	 * 
	 */

	@ApiOperation(value = "Obtener todas las reservas paginados", notes = "Con este metodo conseguimos mandar todas las reservas de 10 en 10. Así la Web podrá recoger los datos mas facilmente.")
	@GetMapping("/reservas")
	public ResponseEntity<?> allReservas(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Reserva> pagina = rs.findAll(pageable);

		// transformar elementos de la pagina a DTO
		Page<ReservaDTO> paginaDTO = pagina.map(new Function<Reserva, ReservaDTO>() {
			@Override
			public ReservaDTO apply(Reserva r) {
				return reservaDTOConverter.convertReservaToReservaDTO(r);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}

	/*
	 * 
	 * GET reserva/{idReserva}
	 * 
	 */

	@ApiOperation(value = "Obtener una reserva por identificador", notes = "Con este metodo conseguimos recoger la información de una Reserva específica.")
	@GetMapping("/reserva/{id}")
	public ResponseEntity<Reserva> getReserva(@PathVariable Integer id) {

		Optional<Reserva> reservaOptional = rs.findById(id);
		if (reservaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(reservaOptional.get());
		} else {
			throw new ReservaNoEncontradoException(id);
		}
	}

	/*
	 * 
	 * GET reserva/{idReserva}
	 * 
	 */

	@ApiOperation(value = "Obtener una reserva por identificador", notes = "Con este metodo conseguimos recoger la información de una Reserva específica.")
	@GetMapping("/reserva/user")
	public ResponseEntity<List<Reserva>> getReservaDeUsuario() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails ud = (UserDetails) authentication.getPrincipal();

		List<Reserva> reservasUser =  rs.getReservasUsuario(ud.getUsername());
		
		return ResponseEntity.status(HttpStatus.OK).body(reservasUser);
		
	}

	/*
	 * 
	 * POST reserva
	 * 
	 */

	@ApiOperation(value = "Añadir una reserva", notes = "Con este metodo conseguimos añadir una Reserva.")
	@PostMapping("/reserva")
	public ResponseEntity<?> postReserva(@RequestBody Reserva nuevaReserva) {

		Reserva r = new Reserva();
		r.setNombre(nuevaReserva.getNombre());
		r.setApellidos(nuevaReserva.getApellidos());
		r.setFecha(nuevaReserva.getFecha());
		r.setHora(nuevaReserva.getHora());
		r.setTelefono(nuevaReserva.getTelefono());

		Optional<Servicio> servicio = ss.findById(nuevaReserva.getServicio().getNombre());

		if (servicio.isPresent()) {

			r.setServicio(servicio.get());
			rs.save(r);

			return ResponseEntity.status(HttpStatus.OK).body("");

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El servicio mandado no existe");
		}
	}

	/*
	 * 
	 * PUT reserva
	 * 
	 */

	@ApiOperation(value = "Modificar una reserva", notes = "Con este metodo conseguimos modificar una Reserva.")
	@PutMapping("/reserva")
	public String putReserva(@RequestBody Reserva editadaReserva) {
		return rs.edit(editadaReserva).toString();
	}

	/*
	 * 
	 * DELETE reserva/{idReserva}
	 * 
	 **/

	@ApiOperation(value = "Borrar una reserva", notes = "Con este metodo conseguimos borrar una Reserva por identificador. De esta forma conseguiremos borrar una Reserva específica.")
	@DeleteMapping("/reserva/{id}")
	public String deleteReserva(@PathVariable Integer id) {
		rs.deleteById(id);
		return "OK";
	}
}
