package io.diego.tech.business;

import io.diego.tech.dto.pessoa.PessoaRestCadastroAnaliseCreditoDTO;
import io.diego.tech.enums.CreditoEnum;
import io.diego.tech.enums.EstadoCivilEnum;
import io.diego.tech.model.Credito;
import io.diego.tech.model.EstadoCivil;
import io.diego.tech.model.Pessoa;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class PessoaBusiness {

	public Pessoa convert(PessoaRestCadastroAnaliseCreditoDTO dto) {
		Pessoa entity = new Pessoa();
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
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

	public static Pessoa calcularCredito(Pessoa entity) {
		entity.setLimiteCredito(BigDecimal.ZERO);
		CreditoEnum creditoEnum = calcularCreditoEnum(entity);
		Credito credito = new Credito();
		credito.setId(creditoEnum.getId());
		entity.setCredito(credito);
		return entity;
	}

	private static CreditoEnum calcularCreditoEnum(Pessoa entity) {
		CreditoEnum creditoEnum = CreditoEnum.REPROVADO;
		if (entity.getEstadoCivil().getId().equals(EstadoCivilEnum.DIVORCIADO.getId())) {
			return creditoEnum;
		}
		if (entity.getEstadoCivil().getId().equals(EstadoCivilEnum.VIUVA.getId()) && entity.getDependentes() > 0) {
			return creditoEnum;
		}

		if (entity.getRenda().compareTo(CreditoEnum.ENTRE_100_500.getFaixaFim()) <= 0) {
			return CreditoEnum.RENDA_BAIXA;
		}

		BigDecimal limiteGastoPorcentagem = new BigDecimal("30");
		BigDecimal fatorAltoSalario = entity.getRenda().compareTo(new BigDecimal("8000")) >= 0 ? new BigDecimal("1.5") : BigDecimal.ONE;

		BigDecimal fatorIdade = new BigDecimal("1.5");
		if (entity.getIdade() < 18) {
			fatorIdade = new BigDecimal("1.0");
		} else if (entity.getIdade() < 25) {
			fatorIdade = new BigDecimal("1.1");
		} else if (entity.getIdade() < 35) {
			fatorIdade = new BigDecimal("1.2");
		} else if (entity.getIdade() < 45) {
			fatorIdade = new BigDecimal("1.3");
		} else if (entity.getIdade() < 55) {
			fatorIdade = new BigDecimal("1.4");
		}

		BigDecimal fatorSexo = entity.getSexo() == 'F' ? new BigDecimal("1.4") : BigDecimal.ONE;
		BigDecimal fatorCasado = entity.getEstadoCivil().getId().equals(EstadoCivilEnum.CASADO.getId()) ? new BigDecimal("1.4") : BigDecimal.ONE;
		BigDecimal fatorDependentes = entity.getDependentes() > 0 ? new BigDecimal(entity.getDependentes()).divide(new BigDecimal("1.15"), RoundingMode.HALF_EVEN) : BigDecimal.ZERO;

		BigDecimal rendaPerCapita = entity.getRenda().divide(fatorDependentes.add(BigDecimal.ONE), RoundingMode.HALF_EVEN);
		BigDecimal limiteCredito = rendaPerCapita.divide(new BigDecimal("100"), RoundingMode.HALF_EVEN).multiply(limiteGastoPorcentagem);
		limiteCredito = limiteCredito.multiply(fatorIdade);
		limiteCredito = limiteCredito.multiply(fatorSexo);
		limiteCredito = limiteCredito.multiply(fatorCasado);
		limiteCredito = limiteCredito.multiply(fatorAltoSalario);

		entity.setLimiteCredito(limiteCredito);

		if (isNaFaixaDeCredito(limiteCredito, CreditoEnum.ENTRE_100_500)) {
			return CreditoEnum.ENTRE_100_500;
		}
		if (isNaFaixaDeCredito(limiteCredito, CreditoEnum.ENTRE_500_1000)) {
			return CreditoEnum.ENTRE_500_1000;
		}
		if (isNaFaixaDeCredito(limiteCredito, CreditoEnum.ENTRE_1000_1500)) {
			return CreditoEnum.ENTRE_1000_1500;
		}
		if (isNaFaixaDeCredito(limiteCredito, CreditoEnum.ENTRE_1500_2000)) {
			return CreditoEnum.ENTRE_1500_2000;
		}
		if (limiteCredito.compareTo(CreditoEnum.SUPERIOR_2000.getFaixaInicio()) > 0) {
			return CreditoEnum.SUPERIOR_2000;
		}
		return creditoEnum;
	}

	private static boolean isNaFaixaDeCredito(BigDecimal limiteGastos, CreditoEnum creditoEnum) {
		return limiteGastos.compareTo(creditoEnum.getFaixaInicio()) >= 0 && limiteGastos.compareTo(creditoEnum.getFaixaFim()) <= 0;
	}
}