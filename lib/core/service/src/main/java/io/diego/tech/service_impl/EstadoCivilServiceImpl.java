package io.diego.tech.service_impl;

import io.diego.lib.spring.data.service.generic.service.GenericServiceImpl;
import io.diego.tech.dto.estadoCivil.EstadoCivilRestDTO;
import io.diego.tech.model.EstadoCivil;
import io.diego.tech.repository.EstadoCivilRepository;
import io.diego.tech.service.EstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoCivilServiceImpl extends GenericServiceImpl<EstadoCivil, Long> implements EstadoCivilService {

	@Autowired
	public EstadoCivilServiceImpl(EstadoCivilRepository repository) {
		super(repository);
	}

	@Override
	protected EstadoCivilRepository getRepository() {
		return (EstadoCivilRepository) super.getRepository();
	}

	@Override
	public List<EstadoCivilRestDTO> findAllDto() {
		return getRepository().findAllDto();
	}
}
