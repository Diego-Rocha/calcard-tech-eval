package io.diego.tech.rest_impl;

import io.diego.tech.enums.EstadoBrasilEnum;
import io.diego.tech.rest.EstadoRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor(
	onConstructor = @__(@Autowired))
public class EstadoRestControllerImpl implements EstadoRestController {

	@Override
	public ResponseEntity<List<EstadoBrasilEnum>> getEstados() {
		return new ResponseEntity<>(Arrays.asList(EstadoBrasilEnum.values()), HttpStatus.OK);
	}

}
