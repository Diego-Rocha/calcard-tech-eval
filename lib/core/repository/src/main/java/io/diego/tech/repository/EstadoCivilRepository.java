package io.diego.tech.repository;

import com.querydsl.jpa.impl.JPAQuery;
import io.diego.lib.spring.data.service.generic.repository.GenericRepository;
import io.diego.tech.dto.estadoCivil.EstadoCivilRestDTO;
import io.diego.tech.dto.estadoCivil.QEstadoCivilRestDTO;
import io.diego.tech.model.EstadoCivil;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.diego.tech.model.QEstadoCivil.estadoCivil;

@Repository
public interface EstadoCivilRepository extends GenericRepository<EstadoCivil, Long> {

	default List<EstadoCivilRestDTO> findAllDto() {
		JPAQuery<EstadoCivilRestDTO> query = new JPAQuery<>(getEntityMananger());
		query.select(new QEstadoCivilRestDTO(estadoCivil.id, estadoCivil.descricao));
		query.from(estadoCivil);
		query.orderBy(estadoCivil.id.asc());
		return query.fetch();
	}

}
