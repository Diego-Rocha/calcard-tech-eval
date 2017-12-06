package io.diego.tech.rest;

import io.diego.tech.dto.estadoCivil.EstadoCivilRestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Api(
	description = "Operações com Estados Civis",
	tags = "estadoCivil")
public interface EstadoCivilController {

	String PATH_ALL_PUBLIC = "${app.security.path.public}/estados-civis";

	@ApiOperation("Retorna a lista de estados civis")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
		path = PATH_ALL_PUBLIC,
		method = RequestMethod.GET)
	ResponseEntity<List<EstadoCivilRestDTO>> getEstadosCivis();

}
