package br.com.gohawk.commons.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CommonsValidationMapper {

	private String attribute;
	private String message;
	private String code;

	public CommonsValidationMapper() {
	}

	public CommonsValidationMapper(String attribute, String message, String code) {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(attribute).append(message).append(code).toString();
	}

}