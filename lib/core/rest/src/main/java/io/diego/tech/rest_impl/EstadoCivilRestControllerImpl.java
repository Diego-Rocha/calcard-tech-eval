package io.diego.tech.rest_impl;

import io.diego.tech.dto.estadoCivil.EstadoCivilRestDTO;
import io.diego.tech.rest.EstadoCivilController;
import io.diego.tech.service.EstadoCivilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(
	onConstructor = @__(@Autowired))
public class EstadoCivilRestControllerImpl implements EstadoCivilController {

	private final EstadoCivilService service;

	@Override
	public ResponseEntity<List<EstadoCivilRestDTO>> getEstadosCivis() {
		return new ResponseEntity<>(service.findAllDto(), HttpStatus.OK);
	}
}
