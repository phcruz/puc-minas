package br.com.phc.pitaco.utilities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnsupportedMediaTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedMediaTypeException() {
		super();
	}

	public UnsupportedMediaTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedMediaTypeException(String message) {
		super(message);
	}

	public UnsupportedMediaTypeException(Throwable cause) {
		super(cause);
	}

}
