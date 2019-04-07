package br.com.gohawk.commons.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommonsExceptionMapper {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsExceptionMapper.class);

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public CommonsExceptionResponse handleException(BadRequestException e) {
		LOG.error(e.getMessage(), e);
		return new CommonsExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public CommonsExceptionResponse handleException(ValidationException e) {
		LOG.warn(e.getMessage(), e);
		return new CommonsExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage(), map(e.getErrors()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public CommonsExceptionResponse handleException(MethodArgumentNotValidException e) {
		LOG.warn(e.getMessage(), e);
		return new CommonsExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
				map(e.getBindingResult().getFieldErrors()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public CommonsExceptionResponse handleException(HttpMessageNotReadableException e) {
		LOG.warn(e.getMessage(), e);
		String message = e.getMessage();
		String cause = "Required request body is missing";
		if (StringUtils.startsWith(e.getMessage(), cause)) {
			message = "Requisição inválida";
		}
		return new CommonsExceptionResponse(HttpStatus.BAD_REQUEST, message);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public CommonsExceptionResponse handleException(ConstraintViolationException e) {
		LOG.error(e.getMessage(), e);
		String message = "Erro ao executar operação no banco de dados";
		return new CommonsExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public CommonsExceptionResponse handleException(DataIntegrityViolationException e) {
		LOG.error(e.getMessage(), e);
		String message = "Erro ao executar operação no banco de dados";
		return new CommonsExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public CommonsExceptionResponse handleException(HttpRequestMethodNotSupportedException e) {
		LOG.error(e.getMessage(), e);
		String message = String.format("Método %s HTTP não suportado pelo servidor esta requisição", e.getMethod());
		return new CommonsExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

	@Order(Ordered.LOWEST_PRECEDENCE)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public CommonsExceptionResponse handleException(Exception e) {
		LOG.error(e.getMessage(), e);
		return new CommonsExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				StringUtils.isBlank(e.getMessage()) ? e.getCause().getClass().getSimpleName() : e.getMessage());
	}

	private List<CommonsValidationMapper> map(List<FieldError> fieldErrors) {
		List<CommonsValidationMapper> erros = new ArrayList<>();
		if (fieldErrors != null) {
			for (FieldError fieldError : fieldErrors) {
				String attribute = new StringBuilder(fieldError.getObjectName()).append(".")
						.append(fieldError.getField()).toString();
				String message = messageSource.getMessage(fieldError, Locale.getDefault());
				String code = fieldError.getCode();
				erros.add(new CommonsValidationMapper(attribute, message, code));
				LOG.warn(attribute + " " + message + " " + code);
			}
		}
		return erros;
	}

}
