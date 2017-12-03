package io.diego.tech.rest;

import io.diego.lib.spring.validator.ValidationException;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.CreditoEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(
	description = "Operações com Pessoas",
	tags = "pessoa")
public interface PessoaRestController {

	String PATH_ALL_PRIVATE = "${app.security.path.private}/pessoas";
	String PATH_ONE_PRIVATE = PATH_ALL_PRIVATE + "/{id}";

	String PATH_ALL_PUBLIC = "${app.security.path.public}/pessoas";
	String PATH_ONE_PUBLIC = PATH_ALL_PUBLIC + "/{id}";

	@ApiOperation("Adiciona uma noticia")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(
		path = PATH_ALL_PUBLIC,
		method = RequestMethod.POST)
	ResponseEntity<CreditoEnum> create(PessoaRestCadastroAnaliseCreditoDTO dto) throws ValidationException;

}
