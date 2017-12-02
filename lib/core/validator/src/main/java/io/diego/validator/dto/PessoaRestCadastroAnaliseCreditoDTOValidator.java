package io.diego.validator.dto;

import io.diego.lib.spring.validator.RequiredField;
import io.diego.lib.spring.validator.Validator;
import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import org.springframework.validation.Errors;

import java.util.Collection;
import java.util.HashSet;

public class PessoaRestCadastroAnaliseCreditoDTOValidator extends Validator<PessoaRestCadastroAnaliseCreditoDTO> {

	@Override
	protected Collection<RequiredField<PessoaRestCadastroAnaliseCreditoDTO>> requiredFields() {
		return new HashSet<RequiredField<PessoaRestCadastroAnaliseCreditoDTO>>() {

			{
				add(getNewRequiredField("nome"));
				add(getNewRequiredField("idade"));
				add(getNewRequiredField("sexo"));
				add(getNewRequiredField("estadoCivilId"));
				add(getNewRequiredField("estado"));
				add(getNewRequiredField("dependentes"));
				add(getNewRequiredField("renda"));
			}
		};
	}

	@Override
	protected void typedValidate(PessoaRestCadastroAnaliseCreditoDTO bean, Errors errors) {

	}

}
