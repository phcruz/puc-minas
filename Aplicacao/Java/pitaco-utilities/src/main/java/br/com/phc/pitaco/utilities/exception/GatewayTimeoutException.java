package br.com.phc.pitaco.utilities.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GatewayTimeoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GatewayTimeoutException() {
		super();
	}

	public GatewayTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public GatewayTimeoutException(String message) {
		super(message);
	}

	public GatewayTimeoutException(Throwable cause) {
		super(cause);
	}

}
