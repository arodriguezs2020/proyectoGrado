package org.jesuitasrioja.Reservas.modelo.servicio;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioDTOConverter {
	@Autowired
	private final ModelMapper modelMapper;

	public ServicioDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ServicioDTO convertServicioToServicioDTO(Servicio servicio) {
		
		ServicioDTO dto = modelMapper.map(servicio, ServicioDTO.class);
		
		return dto;
	}
}
