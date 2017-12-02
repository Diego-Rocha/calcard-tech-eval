package io.diego.tech.service_impl;

import io.diego.lib.spring.data.service.generic.service.GenericServiceImpl;
import io.diego.lib.spring.validator.ValidationException;
import io.diego.tech.enums.CreditoEnum;
import io.diego.tech.model.Credito;
import io.diego.tech.model.Pessoa;
import io.diego.tech.repository.PessoaRepository;
import io.diego.tech.service.PessoaService;
import io.diego.validator.entity.PessoaValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa, Long> implements PessoaService {

	@Autowired
	public PessoaServiceImpl(PessoaRepository repository) {
		super(repository);
	}

	@Override
	protected PessoaRepository getRepository() {
		return (PessoaRepository) super.getRepository();
	}

	@Getter(
			onMethod = @__(@Override))
	private final PessoaValidator validator = new PessoaValidator();

	@Transactional(
			rollbackFor = Throwable.class)
	@Override
	public <S extends Pessoa> S save(S entity) throws ValidationException {
		// TODO implementar motor de aprovacao de credito
		Credito credito = new Credito();
		credito.setId(CreditoEnum.REPROVADO.getId());
		entity.setCredito(credito);
		//TODO remover código acima
		entity = super.save(entity);
		return entity;
	}
}
