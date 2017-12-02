package io.diego.validator.entity;

import io.diego.lib.spring.validator.RequiredField;
import io.diego.lib.spring.validator.Validator;
import io.diego.tech.model.Pessoa;
import org.springframework.validation.Errors;

import java.util.Collection;
import java.util.HashSet;

import static io.diego.tech.model.QPessoa.pessoa;

public class PessoaValidator extends Validator<Pessoa> {

	private static final String ERROR_IDADE_OUT_OF_RANGE = "error.pessoa.idade.outOfRange";
	private static final String ERROR_SEXO_INVALID = "error.pessoa.sexo.invalid";

	@Override
	protected Collection<RequiredField<Pessoa>> requiredFields() {
		return new HashSet<RequiredField<Pessoa>>() {

			{
				add(getNewRequiredField(pessoa.nome.getMetadata().getName()));
				add(getNewRequiredField(pessoa.idade.getMetadata().getName()));
				add(getNewRequiredField(pessoa.sexo.getMetadata().getName()));
				add(getNewRequiredField(pessoa.estadoCivil.getMetadata().getName()));
				add(getNewRequiredField(pessoa.estado.getMetadata().getName()));
				add(getNewRequiredField(pessoa.dependentes.getMetadata().getName()));
				add(getNewRequiredField(pessoa.renda.getMetadata().getName()));
				add(getNewRequiredField(pessoa.creditoId.getMetadata().getName()));
			}
		};
	}

	@Override
	protected void typedValidate(Pessoa entity, Errors errors) {
		if (entity.getIdade() < 1 || entity.getIdade() > 200) {
			errors.rejectValue(pessoa.idade.getMetadata().getName(), ERROR_IDADE_OUT_OF_RANGE);
		}
		if (entity.getSexo() != 'M' && entity.getSexo() != 'F') {
			errors.rejectValue(pessoa.idade.getMetadata().getName(), ERROR_SEXO_INVALID);
		}
	}

}
