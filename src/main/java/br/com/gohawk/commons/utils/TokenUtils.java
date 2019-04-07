package br.com.gohawk.commons.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.gohawk.commons.exception.TokenException;

public class TokenUtils {

	private static final String TAG = TokenUtils.class.getSimpleName();
	private static final Logger LOG = LoggerFactory.getLogger(TokenUtils.class);
	private static final String BEARER = "Bearer";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static String criar(Number id, HttpServletRequest request, HttpServletResponse response) {
		LOG.info(TAG, "criar");
		String message = getToken(id);
		response.addHeader(AUTHORIZATION_HEADER, getTokenHeader(message));
		response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
		return message;
	}

	public static void renovar(HttpServletRequest request, HttpServletResponse response) {
		LOG.info(TAG, "renovar");
		String message = getToken(request);
		Token token = decriptografe(message);
		token.renovar();
		response.addHeader(AUTHORIZATION_HEADER, getTokenHeader(criptografe(token)));
		response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
	}

	public static void invalidar(HttpServletRequest request, HttpServletResponse response) {
		LOG.info(TAG, "invalidar");
		String message = getToken(request);
		Token token = decriptografe(message);
		token.invalidar();
		response.addHeader(AUTHORIZATION_HEADER, getTokenHeader(criptografe(token)));
		response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION_HEADER);
	}

	public static Number getIdUsuarioLogado(HttpServletRequest request) {
		LOG.info(TAG, "getIdUsuarioLogado");
		String message = getToken(request);
		Token token = decriptografe(message);
		return token.getId();
	}

	private static String getToken(HttpServletRequest request) {
		LOG.info(TAG, "getToken");
		String header = request.getHeader(AUTHORIZATION_HEADER);
		if (!StringUtils.isBlank(header) && StringUtils.contains(header, BEARER)) {
			return StringUtils.trim(StringUtils.remove(header, BEARER));
		}
		throw new TokenException("Requisição não possui token");
	}

	private static String getTokenHeader(String message) {
		return String.format("%s %s", BEARER, message);
	}

	private static String criptografe(Token token) {
		String json = JsonUtils.toJson(token);
		return Base64Utils.criptograde(json);
	}

	private static Token decriptografe(String message) {
		message = Base64Utils.decriptografe(message);
		return JsonUtils.toObject(Token.class, message);
	}

	private static String getToken(Number id) {
		Token token = new Token(id);
		return criptografe(token);
	}

	public static class Token {

		public Token() {
		}

		public Token(Number id) {
			setId(id);
			setDataCriacao(new Date());
			setDataExpiracao(new Date(new DateTime(this.dataCriacao).plusMinutes(30).getMillis()));
		}

		private Number id;
		private Date dataCriacao;
		private Date dataExpiracao;

		public Number getId() {
			return id;
		}

		public void setId(Number id) {
			this.id = id;
		}

		public Date getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(Date dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		public Date getDataExpiracao() {
			return dataExpiracao;
		}

		public void setDataExpiracao(Date dataExpiracao) {
			this.dataExpiracao = dataExpiracao;
		}

		public boolean valido() {
			return getDataExpiracao() != null && getDataExpiracao().after(new Date()) && getId() != null
					&& getId().longValue() > 0l;
		}

		public void renovar() {
			if (valido()) {
				setDataExpiracao(new Date(new DateTime(new Date()).plusMinutes(30).getMillis()));
				return;
			}
			throw new RuntimeException("Token inválido");
		}

		public void invalidar() {
			setDataExpiracao(new Date(new DateTime(new Date()).minusYears(10000).getMillis()));
		}

	}
}