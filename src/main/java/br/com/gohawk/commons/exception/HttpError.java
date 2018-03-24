package br.com.gohawk.commons.exception;

import java.util.List;

public class HttpError {

	private String message;
	private List<ValidationMapper> validations;

	public HttpError() {
	}

	public HttpError(String message, List<ValidationMapper> validations) {
		setMessage(message);
		setValidations(validations);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ValidationMapper> getValidations() {
		return validations;
	}

	public void setValidations(List<ValidationMapper> validations) {
		this.validations = validations;
	}

	public static class ValidationMapper {

		private String attribute;
		private String message;
		private String code;

		public ValidationMapper() {
		}

		public ValidationMapper(String attribute, String message, String code) {
			setAttribute(attribute);
			setMessage(message);
			setCode(code);
		}

		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}

}
