package io.diego.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoCivilEnum {
	SOLTEIRO(1L), CASADO(2L), DIVORCIADO(3L), VIUVA(4L);

	private final long id;
}
