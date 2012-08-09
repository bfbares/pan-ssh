package com.borjabares.pan_ssh.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.borjabares.pan_ssh.util.FriendlyTitleGenerator;

public class FriendlyTitleGeneratorTest {

	@Test
	public void generate1(){
		String input = "Esta es uña Prueba" ;
		String expected = "esta-es-una-prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate2(){
		String input = "eSta.--és#otrÁ+prùEba----" ;
		String expected = "esta-es-otra-prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate3(){
		String input = "iÑo,prueba,más+--" ;
		String expected = "ino-prueba-mas";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
}
