package io.diego.tech.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoBrasilEnum {

	AC("Acre"),
	AL("Alagoas"),
	AP("Amapá"),
	BA("Bahia"),
	AM("Amazonas"),
	CE("Ceará"),
	DF("Distrito Federal"),
	ES("Espírito Santo"),
	GO("Goiás"),
	MA("Maranhão"),
	MG("Minas Gerais"),
	MS("Mato Grosso do Sul"),
	MT("Mato Grosso"),
	RR("Roraima"),
	RO("Rondônia"),
	RN("Rio Grande do Norte"),
	SE("Sergipe"),
	SC("Santa Catarina"),
	SP("São Paulo"),
	PA("Pará"),
	PI("Piauí"),
	PB("Paraíba"),
	PE("Pernambuco"),
	PR("Paraná"),
	TO("Tocantins"),
	RJ("Rio de Janeiro"),
	RS("Rio Grande do Sul");

	private final String nome;
}
