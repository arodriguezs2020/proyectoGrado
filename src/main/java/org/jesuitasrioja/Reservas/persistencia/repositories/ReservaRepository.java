package org.jesuitasrioja.Reservas.persistencia.repositories;

import java.util.List;

import org.jesuitasrioja.Reservas.modelo.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

	public List<Reserva> getReservasUsuario();
}
