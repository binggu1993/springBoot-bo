package com.avit.itdap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -975829885598118197L;
	
}