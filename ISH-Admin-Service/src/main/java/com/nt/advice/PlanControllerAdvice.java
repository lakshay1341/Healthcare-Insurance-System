package com.nt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlanControllerAdvice {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public   ResponseEntity<ExceptionInfo>  handleIAE(IllegalArgumentException iae){
		   ExceptionInfo info=new ExceptionInfo();
		   info.setMessage(iae.getMessage());
		   info.setCode(3000);
		return new  ResponseEntity(info,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(Exception.class)
	public    ResponseEntity<ExceptionInfo>   handleAllExceptions(Exception  e){
		   ExceptionInfo info=new ExceptionInfo();
		   info.setMessage(e.getMessage());
		   info.setCode(5000);
		return new  ResponseEntity(info,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
