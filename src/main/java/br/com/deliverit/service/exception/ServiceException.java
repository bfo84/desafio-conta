package br.com.deliverit.service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @author brunofo 
 * Bruno Franco Oliveira
 *
 */
public class ServiceException extends RuntimeException {

	
	private static final long serialVersionUID = -7010976115488796961L;

	public ServiceException() {
		super(); 
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, cause, enableSuppresion, writableStackTrace);
	}

	public ServiceException(Exception ex, Object object, HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated constructor stub
	}
	
}
