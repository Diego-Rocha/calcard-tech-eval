package io.diego.tech.rest_impl;

import io.diego.lib.spring.validator.ValidationException;
import io.diego.tech.business.PessoaBusiness;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.CreditoEnum;
import io.diego.tech.model.Pessoa;
import io.diego.tech.rest.PessoaRestController;
import io.diego.tech.service.PessoaService;
import io.diego.validator.dto.PessoaRestCadastroAnaliseCreditoDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(
	onConstructor = @__(@Autowired))
public class PessoaRestControllerImpl implements PessoaRestController {

	private final PessoaService service;

	private final PessoaRestCadastroAnaliseCreditoDTOValidator validator = new PessoaRestCadastroAnaliseCreditoDTOValidator();

	@InitBinder("pessoaRestCadastroAnaliseCreditoDTO")
	protected void bind(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@Override
	public ResponseEntity<CreditoEnum> create(@Validated @RequestBody PessoaRestCadastroAnaliseCreditoDTO dto) throws ValidationException {
		Pessoa entity = PessoaBusiness.convert(dto);
		Pessoa savedEntity = service.save(entity);
		CreditoEnum creditoEnum = CreditoEnum.getById(savedEntity.getCredito().getId());
		return new ResponseEntity<>(creditoEnum, HttpStatus.CREATED);
	}

}
