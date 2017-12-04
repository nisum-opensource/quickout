/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/
package com.nisum.quickout;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nisum.quickout.exception.QuickoutInvalidInputException;
import com.nisum.quickout.exception.QuickoutNotFoundException;

/**
 * Central location to handle exceptions from all controllers. If an exception does not have its own message
 * the error_codes.properties file is refered for a general error code.
 * 
 * @author Riaz Uddin
 *
 */
@ControllerAdvice
@PropertySource("classpath:error_codes.properties")
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private Environment env;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = QuickoutInvalidInputException.class)
	public ResponseEntity<?> invalidInput(QuickoutInvalidInputException e) {
		return getErrorResponse(env.getProperty("invalid.input"), HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(value = QuickoutNotFoundException.class)
	public ResponseEntity<?> entityNotFound(QuickoutNotFoundException e) {
		return getErrorResponse(env.getProperty("not.found"), HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		return getErrorResponse(env.getProperty("unknown.error"), HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	private ResponseEntity<?> getErrorResponse(String msg, HttpStatus status, Exception e) {
		String errorKey = String.format("Incident[%s] ", UUID.randomUUID().toString());
		String errorMsg = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : msg;
		logger.error(errorKey, e);
		return ResponseEntity.status(status).body(String.format("%s- %s", errorKey, errorMsg));
	}
}
