package com.bridgelabz.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.noteservice.model.Response;
/**
 * Purpose : Class to handle all exception of the the class.
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since  
 */
@ControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler
	public ResponseEntity<Response> handleOtherException(Exception exception){
		
		Response response = new Response();
		
		response.setMessage(exception.getMessage());
		response.setStatus(-1);
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
