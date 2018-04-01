package br.com.gohawk.commons.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

	public static <T> String toJson(T t) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	public static <T> T toObject(Class<T> classe, String json) {
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			ObjectReader reader = mapper.readerFor(classe);
			return reader.readValue(json);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

}
