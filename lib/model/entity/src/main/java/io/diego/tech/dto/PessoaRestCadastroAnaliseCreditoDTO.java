package io.diego.tech.dto;

import io.diego.tech.enums.EstadoBrasilEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PessoaRestCadastroAnaliseCreditoDTO {

	private String nome;
	private int idade;
	private char sexo;
	private Long estadoCivilId;
	private EstadoBrasilEnum estado;
	private int dependentes;
	private BigDecimal renda;

}
