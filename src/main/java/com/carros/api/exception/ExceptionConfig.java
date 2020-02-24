package com.carros.api.exception;

import java.io.Serializable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<String> errorNotFound(Exception ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<String> errorBadRequest(Exception ex) {
		return ResponseEntity.badRequest().build();
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionError exceptionErro = new ExceptionError("Operação não permitida", status.value());
		return new ResponseEntity<Object>(exceptionErro, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
}

class ExceptionError implements Serializable {

	private static final long serialVersionUID = 4868147036731761132L;
	public String error;
	public int status;

	public ExceptionError(String error, int status) {
		this.error = error;
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public int getStatus() {
		return status;
	}

}
