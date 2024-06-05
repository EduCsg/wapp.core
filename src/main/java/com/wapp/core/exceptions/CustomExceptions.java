package com.wapp.core.exceptions;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wapp.core.models.ResponseModel;

@ControllerAdvice
public class CustomExceptions {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ResponseModel response = new ResponseModel();

		response.setStatus("400");
		response.setSuccess(false);
		response.setMessage(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());

		return ResponseEntity.status(400).body(response);
	}

}
