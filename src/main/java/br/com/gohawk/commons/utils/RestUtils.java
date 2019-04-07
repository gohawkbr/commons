package br.com.gohawk.commons.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.gohawk.commons.exception.CommonsExceptionResponse;
import br.com.gohawk.commons.exception.RestException;

public class RestUtils {

	private RestTemplate template;
	private HttpEntity<?> entity;
	private HttpHeaders headers;

	public RestUtils() {
		this.template = new RestTemplate();
	}

	public RestUtils(RestTemplate template) {
		this.template = template;
	}

	private <T> T execute(String uri, HttpMethod method, Class<T> clazz) throws RestException {
		ResponseEntity<String> response = template.exchange(uri, method, this.entity, String.class);
		this.headers = response.getHeaders();
		if (!HttpStatus.OK.equals(response.getStatusCode())) {
			CommonsExceptionResponse appExceptionResponse = JsonUtils.toObject(CommonsExceptionResponse.class,
					response.getBody());
			throw new RestException(appExceptionResponse.getMessage());
		}
		return JsonUtils.toObject(clazz, response.getBody());

	}

	public <T> T get(String uri, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(new HttpHeaders());
		return execute(uri, HttpMethod.GET, clazz);
	}

	public <T> T get(String uri, Class<T> clazz, HttpHeaders headers) throws RestException {
		this.entity = new HttpEntity<Object>(headers);
		return execute(uri, HttpMethod.GET, clazz);
	}

	public <T> T post(String uri, Object body, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(body, new HttpHeaders());
		return execute(uri, HttpMethod.POST, clazz);
	}

	public <T> T post(String uri, Object body, Class<T> clazz, HttpHeaders headers) throws RestException {
		this.entity = new HttpEntity<Object>(body, headers);
		return execute(uri, HttpMethod.POST, clazz);
	}

	public <T> T put(String uri, Object body, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(body, new HttpHeaders());
		return execute(uri, HttpMethod.PUT, clazz);
	}

	public <T> T put(String uri, Object body, Class<T> clazz, HttpHeaders headers) throws RestException {
		this.entity = new HttpEntity<Object>(body, headers);
		return execute(uri, HttpMethod.PUT, clazz);
	}

	public <T> T delete(String uri, Class<T> clazz) throws RestException {
		this.entity = new HttpEntity<Object>(new HttpHeaders());
		return execute(uri, HttpMethod.DELETE, clazz);
	}

	public <T> T delete(String uri, Class<T> clazz, HttpHeaders headers) throws RestException {
		this.entity = new HttpEntity<Object>(headers);
		return execute(uri, HttpMethod.DELETE, clazz);
	}

	public HttpEntity<?> getEntity() {
		return this.entity;
	}

	public HttpHeaders getHeaders() {
		return this.headers;
	}

}
