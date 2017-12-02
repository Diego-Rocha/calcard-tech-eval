package io.diego.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CreditoEnum {
	REPROVADO(1L), RENDA_BAIXA(2L), ENTRE_500_1000(3L), ENTRE_1000_1500(4L), SUPERIOR_2000(4L);

	private final long id;
}
