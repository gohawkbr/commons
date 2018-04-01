package br.com.gohawk.commons.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {

	private static final Logger LOG = LoggerFactory.getLogger(MD5Utils.class);

	public static String getMD5(String message) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(message.getBytes(), 0, message.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

}
