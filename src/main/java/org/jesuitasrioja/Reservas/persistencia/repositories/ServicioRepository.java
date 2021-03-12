package org.jesuitasrioja.Reservas.persistencia.repositories;

import org.jesuitasrioja.Reservas.modelo.servicio.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, String>{

}
