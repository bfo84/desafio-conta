package br.com.deliverit.exception.handler;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.deliverit.service.exception.ServiceException;
import br.com.deliverit.service.generic.GenericResponse;

/**
 * 
 * @author brunofo 
 * Bruno Franco Oliveira
 *
 */
@ControllerAdvice
public class HandlerException {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ ServiceException.class })
	protected final ResponseEntity<GenericResponse> serviceHandleException(Exception ex) {
		if (ex instanceof ServiceException) {
			HttpHeaders headers = new HttpHeaders();
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return serviceExceptionInternal(ex, headers, status);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ResponseEntity<GenericResponse> serviceExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status) {

		Logger logger = LoggerFactory.getLogger(ex.getClass());
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			logger.error(ex.getMessage(), ex);
			GenericResponse response = new GenericResponse();
			List<String> errors = new ArrayList<String>();
			errors.add(ex.getMessage());
			response.setErrors(errors);
			return new ResponseEntity<GenericResponse>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;

	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ SQLException.class , SQLGrammarException.class, 
		SQLSyntaxErrorException.class, InvalidDataAccessResourceUsageException.class, 
		HibernateException.class, PersistenceException.class})
	protected final ResponseEntity<GenericResponse> repositoryeHandleException(Exception ex) {
		HttpHeaders headers = new HttpHeaders();

		if (ex instanceof SQLException || ex instanceof SQLGrammarException
				||ex instanceof SQLSyntaxErrorException 
				|| ex instanceof InvalidDataAccessResourceUsageException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return repositoryExceptionInternal(ex, headers, status);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	private ResponseEntity<GenericResponse> repositoryExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status){

		Logger logger = LoggerFactory.getLogger(ex.getClass());
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			GenericResponse response = getResponse("Erro ao acessar o banco de dados");
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<GenericResponse>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;

	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ Exception.class })
	protected final ResponseEntity<GenericResponse> genericHandleException(Exception ex){
		HttpHeaders headers = new HttpHeaders();

		if (ex instanceof Exception) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return genericExceptionInternal(ex, headers, status);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes" })
	private ResponseEntity<GenericResponse> genericExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status) {

		Logger logger = LoggerFactory.getLogger(ex.getClass());
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			GenericResponse response = getResponse("Houve um erro interno do servidor");
			logger.error(ex.getMessage(), ex);
			return new ResponseEntity<GenericResponse>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;

	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ ConstraintViolationException .class, MethodArgumentNotValidException.class})
	protected final ResponseEntity<GenericResponse> beanValidationHandleException(MethodArgumentNotValidException ex){
		HttpHeaders headers = new HttpHeaders();
		
		if (ex instanceof Exception) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return beanValidationExceptionInternal(ex, headers, status);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes"})
	private ResponseEntity<GenericResponse> beanValidationExceptionInternal(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
		
		Logger logger = LoggerFactory.getLogger(ex.getClass());
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status) && ex.getBindingResult() != null
			&& ex.getBindingResult().getAllErrors() != null &&	!ex.getBindingResult().getAllErrors().isEmpty()) {
			
		    Set<String> violationMessages = new HashSet<String>();

		    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
		        violationMessages.add(error.getDefaultMessage());
		    }
		    String messages = StringUtils.join(violationMessages, "\n");
		    GenericResponse response = getResponse(messages);
		    logger.error(messages, ex);

			return new ResponseEntity<GenericResponse>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GenericResponse getResponse(String messages) {
		GenericResponse response = new GenericResponse();
		List<String> errors = new ArrayList<String>();
		errors.add(messages);
		response.setErrors(errors);
		return response;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/error")
	protected ResponseEntity<GenericResponse> notFound(Exception ex){
		GenericResponse response = getResponse("Não existe o serviço");
		return new ResponseEntity<GenericResponse>(response, HttpStatus.NOT_FOUND);
	}

}