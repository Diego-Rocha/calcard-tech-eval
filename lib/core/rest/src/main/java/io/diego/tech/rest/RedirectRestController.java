package io.diego.tech.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RedirectRestController {

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
		path = "/",
		method = RequestMethod.GET)
	void redirectToSwagger(HttpServletResponse httpServletResponse) throws IOException;
}
