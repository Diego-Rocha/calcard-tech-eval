package io.diego.tech.rest;

import io.diego.lib.spring.validator.ValidationException;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.dto.PessoaRestRetornoAnaliseCreditoDTO;
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

	String PATH_ALL_PUBLIC = "${app.security.path.public}/pessoas";

	@ApiOperation("Adiciona uma pessoa")
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(
		path = PATH_ALL_PUBLIC,
		method = RequestMethod.POST)
	ResponseEntity<PessoaRestRetornoAnaliseCreditoDTO> create(PessoaRestCadastroAnaliseCreditoDTO dto) throws ValidationException;

	@ApiOperation("Retorna uma pessoa pelo cpf")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
		path = PATH_ALL_PUBLIC + "/cpf/{cpf}",
		method = RequestMethod.GET)
	ResponseEntity<PessoaRestRetornoAnaliseCreditoDTO> getByCpf(String cpf);

}
