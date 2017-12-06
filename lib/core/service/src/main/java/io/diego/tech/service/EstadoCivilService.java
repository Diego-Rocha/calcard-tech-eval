package io.diego.tech.service;

import io.diego.lib.spring.data.service.generic.service.GenericService;
import io.diego.tech.dto.estadoCivil.EstadoCivilRestDTO;
import io.diego.tech.model.EstadoCivil;

import java.util.List;

public interface EstadoCivilService extends GenericService<EstadoCivil, Long> {

	List<EstadoCivilRestDTO> findAllDto();
}
