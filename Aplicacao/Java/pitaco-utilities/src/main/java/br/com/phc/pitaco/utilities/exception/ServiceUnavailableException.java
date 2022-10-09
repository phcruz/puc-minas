package br.com.phc.pitaco.utilities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceUnavailableException() {
		super();
	}

	public ServiceUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

	public ServiceUnavailableException(Throwable cause) {
		super(cause);
	}

}
