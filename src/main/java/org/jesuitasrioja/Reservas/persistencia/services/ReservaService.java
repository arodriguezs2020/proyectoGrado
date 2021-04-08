package org.jesuitasrioja.Reservas.persistencia.services;

import java.util.ArrayList;
import java.util.List;

import org.jesuitasrioja.Reservas.modelo.reserva.Reserva;
import org.jesuitasrioja.Reservas.modelo.user.UserEntity;
import org.jesuitasrioja.Reservas.persistencia.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservaService extends BaseService<Reserva, Integer, ReservaRepository> {
	
	public List<Reserva> getReservasUsuario(String username){
		
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		List<Reserva> todasLasReservas = findAll();
		
		for (Reserva reserva : todasLasReservas) {
			UserEntity usuario = reserva.getUsuario();
			
			if(usuario.getUsername().equals(username)) {
				reservas.add(reserva);
			}
			
		}
		
		return reservas;
	}

}
