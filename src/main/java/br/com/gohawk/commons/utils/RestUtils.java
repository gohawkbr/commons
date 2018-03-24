package br.com.gohawk.commons.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.gohawk.commons.exception.HttpError;
import br.com.gohawk.commons.exception.RestException;

public class RestUtils {

	private HttpHeaders headers;
	private HttpEntity<?> entity;
	private RestTemplate template;

	public RestUtils() {
		this.template = new RestTemplate();
		this.headers = new HttpHeaders();
		this.entity = new HttpEntity<String>(headers);
	}

	public RestUtils(RestTemplate template) {
		this.template = template;
		this.headers = new HttpHeaders();
		this.entity = new HttpEntity<String>(headers);
	}

	private <T> T execute(String uri, HttpMethod method, Class<T> clazz) throws RestException {
		ResponseEntity<String> response = template.exchange(uri, method, entity, String.class);
		if (!HttpStatus.OK.equals(response.getStatusCode())) {
			HttpError error = JsonUtils.toObject(HttpError.class, response.getBody());
			throw new RestException(error.getMessage(), error);
		}
		return JsonUtils.toObject(clazz, response.getBody());

	}

	public <T> T get(String uri, Class<T> clazz) throws RestException {
		return execute(uri, HttpMethod.GET, clazz);
	}

	public <T> T post(String uri, Object body, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(body, this.headers);
		return execute(uri, HttpMethod.POST, clazz);
	}

	public <T> T put(String uri, Object body, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(body, this.headers);
		return execute(uri, HttpMethod.PUT, clazz);
	}

	public <T> T delete(String uri, Class<T> clazz) throws RestException {
		return execute(uri, HttpMethod.DELETE, clazz);
	}

}
