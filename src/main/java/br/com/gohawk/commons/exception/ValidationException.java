package br.com.gohawk.commons.exception;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 6926813127151955168L;

	private List<FieldError> errors;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, BindingResult result) {
		super(message);
		errors = result.getFieldErrors();
	}

	public List<FieldError> getErrors() {
		return this.errors;
	}

}
