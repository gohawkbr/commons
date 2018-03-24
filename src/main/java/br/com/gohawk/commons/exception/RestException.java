package br.com.gohawk.commons.exception;

public class RestException extends Exception {

	private static final long serialVersionUID = -135582864527335474L;

	private HttpError error;

	public RestException(String message, HttpError error) {
		super(message);
		this.error = error;
	}

	public HttpError getError() {
		return this.error;
	}

}
