package io.diego.tech.rest_impl;

import io.diego.tech.rest.RedirectRestController;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RedirectRestControllerImpl implements RedirectRestController {

	@Override
	public void redirectToSwagger(HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendRedirect("/swagger-ui.html");
	}
}
