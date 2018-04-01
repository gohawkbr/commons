package br.com.gohawk.commons.exception;

public class TokenException extends RuntimeException {

	private static final long serialVersionUID = -3104599067577066732L;

	public TokenException(String message) {
		super(message);
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
