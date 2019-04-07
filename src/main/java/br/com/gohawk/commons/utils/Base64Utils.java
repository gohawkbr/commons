package br.com.gohawk.commons.utils;

import java.util.Base64;

public class Base64Utils {

	public static String criptograde(String message) {
		byte[] encodedBytes = Base64.getEncoder().encode(message.getBytes());
		return new String(encodedBytes);

	}

	public static String decriptografe(String message) {
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		return new String(decodedBytes);
	}

}
