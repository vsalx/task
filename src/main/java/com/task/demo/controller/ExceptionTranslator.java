package com.task.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.task.demo.exception.PreconditionFailedException;

@ControllerAdvice
public class ExceptionTranslator {
	
	@ExceptionHandler({PreconditionFailedException.class, MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> exceptionHandler(final Exception e) {
		return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
	}
}
