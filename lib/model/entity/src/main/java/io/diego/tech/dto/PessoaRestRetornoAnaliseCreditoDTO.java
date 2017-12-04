package io.diego.tech.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor(
	onConstructor = @__(@QueryProjection))
@NoArgsConstructor
public class PessoaRestRetornoAnaliseCreditoDTO {

	private String cpf;
	private String nome;
	private BigDecimal limiteCredito;
	private boolean creditoAprovado;
	private String creditoDescricao;

}
