package br.com.gohawk.commons.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

public class URIUtils {

	private UriComponentsBuilder builder;
	private Map<String, Number> pathParams;

	public URIUtils(String url) {
		this.builder = UriComponentsBuilder.fromUriString(url);

	}

	/**
	 * Informa PathParams à url<br>
	 * Para url definida como /documento/{id}
	 * 
	 * <pre>
	 * setPathParam("id", 10);
	 * </pre>
	 * 
	 * Para url definida como /veiculo/{id}/modelo/{idModelo}
	 * 
	 * <pre>
	 * setPathParam("id", 10); <br>
	 * setPathParam("idModelo", 654897); <br>
	 * </pre>
	 * 
	 * @param pathParam
	 * @param valueParam
	 */
	public URIUtils addPathParam(String pathParam, Number valueParam) {
		if (this.pathParams == null) {
			this.pathParams = new HashMap<>();
		}
		this.pathParams.put(pathParam, valueParam);
		return this;
	}

	/**
	 * Informa QuryParams <br>
	 * 
	 * <pre>
	 * setQueryParams("idSituacaoDocumento", 10);
	 * setQueryParams("dataContrato", new Date());
	 * setQueryParams("idVeiculos", new Integer[] { 1, 2, 3 });
	 * </pre>
	 * 
	 * @param queryParam
	 * @param queryValue
	 */
	public URIUtils addQueryParam(String queryParam, Object... queryValue) {
		this.builder.queryParam(queryParam, queryValue);
		return this;
	}

	/**
	 * Retorna a URI completa
	 * 
	 * @return
	 */
	public String build() {
		return this.pathParams != null ? this.builder.buildAndExpand(pathParams).toUriString()
				: this.builder.build().toUriString();
	}
}
