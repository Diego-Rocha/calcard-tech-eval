package io.diego.tech.business;

import io.diego.tech.dto.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.model.EstadoCivil;
import io.diego.tech.model.Pessoa;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PessoaBusiness {

	public Pessoa convert(PessoaRestCadastroAnaliseCreditoDTO dto) {
		Pessoa entity = new Pessoa();
		entity.setNome(dto.getNome());
		entity.setIdade(dto.getIdade());
		entity.setSexo(dto.getSexo());
		if (dto.getEstadoCivilId() != null) {
			EstadoCivil estadoCivil = new EstadoCivil();
			estadoCivil.setId(dto.getEstadoCivilId());
			entity.setEstadoCivil(estadoCivil);
		}
		entity.setEstado(dto.getEstado());
		entity.setDependentes(dto.getDependentes());
		entity.setRenda(dto.getRenda());
		return entity;
	}

}
