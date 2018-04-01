package br.com.gohawk.commons.exception;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

public class CommonsExceptionResponse {

	private HttpStatus status;
	private String message;
	private List<CommonsValidationMapper> validations;
	
	public CommonsExceptionResponse(){}

	public CommonsExceptionResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public CommonsExceptionResponse(HttpStatus status, String message, List<CommonsValidationMapper> validations) {
		this.status = status;
		this.message = message;
		this.validations = validations;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<CommonsValidationMapper> getValidations() {
		return this.validations;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this).append(status.value()).append(message);
		if (getValidations() != null && !getValidations().isEmpty()) {
			builder.append(getValidations());
		}
		return builder.toString();
	}

}
