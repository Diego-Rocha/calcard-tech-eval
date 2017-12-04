package io.diego.tech.service;

import io.diego.lib.spring.data.service.generic.service.GenericService;
import io.diego.tech.dto.PessoaRestRetornoAnaliseCreditoDTO;
import io.diego.tech.model.Pessoa;

public interface PessoaService extends GenericService<Pessoa, Long> {

	PessoaRestRetornoAnaliseCreditoDTO findStatusByCpf(String cpf);
}
