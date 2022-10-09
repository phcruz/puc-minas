package br.com.phc.pitaco.notification.handler;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.phc.pitaco.utilities.dto.ErrorDetailsDTO;
import br.com.phc.pitaco.utilities.exception.BadRequestException;
import br.com.phc.pitaco.utilities.exception.CustomException;
import br.com.phc.pitaco.utilities.exception.ForbiddenException;
import br.com.phc.pitaco.utilities.exception.GatewayTimeoutException;
import br.com.phc.pitaco.utilities.exception.InternalServerException;
import br.com.phc.pitaco.utilities.exception.NotFoundException;
import br.com.phc.pitaco.utilities.exception.ServiceUnavailableException;
import br.com.phc.pitaco.utilities.exception.UnauthorizedException;
import br.com.phc.pitaco.utilities.exception.UnsupportedMediaTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> exceptionCustomizada(CustomException e, HttpServletRequest request) {
		log.info(e.getMessage());
		
		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(e.getHttpStatus().name());
		details.setStatusCode(e.getHttpStatus().value());
		details.setTime(Calendar.getInstance().getTimeInMillis());
		
		return new ResponseEntity<>(convertErrorInJson(details), e.getHttpStatus());
	}
	
	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<String> handlerBadRequestException(BadRequestException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.BAD_REQUEST.name());
		details.setStatusCode(HttpStatus.BAD_REQUEST.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());
		
		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ UnauthorizedException.class })
	public ResponseEntity<String> handlerUnauthorizedException(UnauthorizedException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.UNAUTHORIZED.name());
		details.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({ ForbiddenException.class })
	public ResponseEntity<String> handlerForbiddenException(ForbiddenException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.FORBIDDEN.name());
		details.setStatusCode(HttpStatus.FORBIDDEN.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<String> handlerNotFoundException(NotFoundException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.NOT_FOUND.name());
		details.setStatusCode(HttpStatus.NOT_FOUND.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({ UnsupportedMediaTypeException.class })
	public ResponseEntity<String> handlerUnsupportedMediaTypeException(UnsupportedMediaTypeException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name());
		details.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler({ InternalServerException.class })
	public ResponseEntity<String> handlerInternalServerException(InternalServerException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
		details.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ ServiceUnavailableException.class })
	public ResponseEntity<String> handlerServiceUnavailableException(ServiceUnavailableException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.SERVICE_UNAVAILABLE.name());
		details.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler({ GatewayTimeoutException.class })
	public ResponseEntity<String> handlerGatewayTimeoutException(GatewayTimeoutException e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.GATEWAY_TIMEOUT.name());
		details.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.GATEWAY_TIMEOUT);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<String> handlerGenericException(Exception e, WebRequest request, HttpServletResponse response) {
		log.info(e.getMessage());

		ErrorDetailsDTO details = new ErrorDetailsDTO();
		details.setUniqueId(UUID.randomUUID().toString());
		details.setMessage(e.getMessage());
		details.setInformationCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
		details.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		details.setTime(Calendar.getInstance().getTimeInMillis());

		return new ResponseEntity<>(convertErrorInJson(details), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public String convertErrorInJson(ErrorDetailsDTO details) {
		try {
			return new ObjectMapper().writeValueAsString(details);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}
}
