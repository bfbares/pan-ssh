package com.borjabares.pan_ssh.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.borjabares.pan_ssh.util.PasswordCodificator;

public class PasswordCodificatorTest {
	
	@Test
	public void codify() throws Exception{
		String password1 = "EstaEsUnaPrueba123";
		String password2 = password1;
		
		password1 = PasswordCodificator.codify(password1);
		password2 = PasswordCodificator.codify(password2);
		
		assertEquals(password1, password2);
	}

	@Test
	public void codifyError() throws Exception{
		String password1 = "EstaEsUnaPrueba123";
		String password2 = "EstaEsUnaprueba123";
		
		password1 = PasswordCodificator.codify(password1);
		password2 = PasswordCodificator.codify(password2);
		
		assertFalse(password2.equals(password1));
	}
}
