package br.com.gohawk.commons.test.validator;

import org.junit.Assert;
import org.junit.Test;

import br.com.gohawk.commons.validator.EmailValidator;

public class EmailValidatorTest {

	@Test
	public void emailValido() {
		Assert.assertTrue(EmailValidator.isValid("usuario@email.com"));
	}

	@Test
	public void emailInvalido() {
		Assert.assertFalse(EmailValidator.isValid("usuario.email.com"));
		Assert.assertFalse(EmailValidator.isValid("usuario@email"));
		Assert.assertFalse(EmailValidator.isValid("usuario@"));
		Assert.assertFalse(EmailValidator.isValid("@email.com"));
		Assert.assertFalse(EmailValidator.isValid("usuario@email."));
	}

}
