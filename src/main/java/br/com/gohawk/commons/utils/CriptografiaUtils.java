package br.com.gohawk.commons.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriptografiaUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CriptografiaUtils.class);
	private SecretKey chave;

	public CriptografiaUtils(String storePass, String alias, String keyPass, InputStream jceksFile) {
		try {
			KeyStore jceks = KeyStore.getInstance("JCEKS");
			jceks.load(jceksFile, storePass.toCharArray());
			chave = (SecretKey) jceks.getKey(alias, keyPass.toCharArray());
		} catch (Exception e) {
			LOG.error("Não foi possivel instanciar CriptografiaUtils", e.getMessage());
		}

	}

	public String criptografe(final String mensagem) {
		try {
			final Cipher cifrador = Cipher.getInstance(chave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, chave);
			return new String(Base64.getEncoder().encode(cifrador.doFinal(mensagem.getBytes())));
		} catch (Exception e) {
			final String mensagemErro = "Não foi possível criptografar a mensagem.";
			LOG.error(mensagemErro, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public String criptografeHexadecimal(final String mensagem) {
		try {
			final Cipher cifrador = Cipher.getInstance(chave.getAlgorithm());
			cifrador.init(Cipher.ENCRYPT_MODE, chave);
			return new String(Hex.encodeHex(cifrador.doFinal(mensagem.getBytes()))).toUpperCase();
		} catch (Exception e) {
			final String mensagemErro = "Não foi possível criptografar a mensagem.";
			LOG.error(mensagemErro, e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public String descriptografe(final String mensagem) {
		try {
			final Cipher decifrador = Cipher.getInstance(chave.getAlgorithm());
			decifrador.init(Cipher.DECRYPT_MODE, chave);
			return new String(decifrador.doFinal(Base64.getDecoder().decode(mensagem.getBytes())), "UTF-8");
		} catch (final Exception e) {
			final String mensagemErro = "Não foi possível descriptografar a mensagem.";
			LOG.error(mensagemErro, e);
			throw new RuntimeException(mensagemErro, e);
		}

	}

	public String descriptografeHexadecimal(final String mensagem) {
		try {
			final Cipher decifrador = Cipher.getInstance(chave.getAlgorithm());
			decifrador.init(Cipher.DECRYPT_MODE, chave);
			decifrador.doFinal(Hex.decodeHex(mensagem.toCharArray()));
			return new String(decifrador.doFinal(Hex.decodeHex(mensagem.toCharArray())));
		} catch (final Exception e) {
			final String mensagemErro = "Não foi possível descriptografar a mensagem.";
			LOG.error(mensagemErro, e);
			throw new RuntimeException(mensagemErro, e);
		}

	}
}
