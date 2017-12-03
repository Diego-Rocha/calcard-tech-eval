package io.diego.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public enum CreditoEnum {
	REPROVADO(1L, false, null, null),
	RENDA_BAIXA(2L, false, null, new BigDecimal("100")),
	ENTRE_100_500(3L, true, new BigDecimal("100"), new BigDecimal("500")),
	ENTRE_500_1000(4L, true, new BigDecimal("500"), new BigDecimal("1000")),
	ENTRE_1000_1500(5L, true, new BigDecimal("1000"), new BigDecimal("1500")),
	ENTRE_1500_2000(6L, true, new BigDecimal("1500"), new BigDecimal("2000")),
	SUPERIOR_2000(7L, true, new BigDecimal("2000"), null);

	private final Long id;
	private final boolean aprovado;
	private final BigDecimal faixaInicio;
	private final BigDecimal faixaFim;

	public static CreditoEnum getById(Long id) {
		for (CreditoEnum creditoEnum : values()) {
			if (creditoEnum.id.equals(id)) {
				return creditoEnum;
			}
		}
		return null;
	}

}
