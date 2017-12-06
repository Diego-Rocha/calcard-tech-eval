package io.diego.tech.dto.estadoCivil;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(
	onConstructor = @__(@QueryProjection))
@NoArgsConstructor
public class EstadoCivilRestDTO {

	private Long id;
	private String descricao;

}
