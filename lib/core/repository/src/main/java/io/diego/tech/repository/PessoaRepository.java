package io.diego.tech.repository;

import com.querydsl.jpa.impl.JPAQuery;
import io.diego.lib.spring.data.service.generic.repository.GenericRepository;
import io.diego.tech.dto.PessoaRestRetornoAnaliseCreditoDTO;
import io.diego.tech.dto.QPessoaRestRetornoAnaliseCreditoDTO;
import io.diego.tech.model.Pessoa;
import io.diego.tech.model.QCredito;
import org.springframework.stereotype.Repository;

import static io.diego.tech.model.QCredito.credito;
import static io.diego.tech.model.QPessoa.pessoa;

@Repository
public interface PessoaRepository extends GenericRepository<Pessoa, Long> {

	default PessoaRestRetornoAnaliseCreditoDTO findStatusByCpf(String cpf) {
		JPAQuery<PessoaRestRetornoAnaliseCreditoDTO> query = new JPAQuery<>(getEntityMananger());
		query.select(new QPessoaRestRetornoAnaliseCreditoDTO(pessoa.cpf, pessoa.nome, pessoa.limiteCredito, credito.aprovado, credito.descricao));
		query.from(pessoa);
		query.innerJoin(pessoa.credito, credito);
		query.where(pessoa.cpf.eq(cpf));
		return query.fetchFirst();
	}

}
