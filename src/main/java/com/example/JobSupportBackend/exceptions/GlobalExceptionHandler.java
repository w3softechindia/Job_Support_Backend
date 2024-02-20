package com.example.JobSupportBackend.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidAdminException.class)
	public ResponseEntity<?> handleAdminException(InvalidAdminException adminException, WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new Date(), adminException.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidIdException.class)
	public ResponseEntity<?> handleIdException(InvalidIdException idException, WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new Date(), idException.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new Date(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	}
}
