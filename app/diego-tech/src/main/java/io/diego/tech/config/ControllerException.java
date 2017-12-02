package io.diego.tech.config;

import io.diego.tech.exception.BusinessException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletRequestAttributes;;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class ControllerException {

	@ExceptionHandler({ BusinessException.class })
	protected ResponseEntity<Map<String, Object>> handleRestBusinessException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BusinessException ex) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpServletRequest, httpServletResponse);

		Map<String, Object> errorAttributes = new DefaultErrorAttributes().getErrorAttributes(servletRequestAttributes, false);
		errorAttributes.put("status", httpStatus.value());
		errorAttributes.put("error", httpStatus.getReasonPhrase());
		errorAttributes.put("message", ex.getMessage());
		errorAttributes.put("path", httpServletRequest.getRequestURI());

		return new ResponseEntity<>(errorAttributes, httpStatus);
	}

}
