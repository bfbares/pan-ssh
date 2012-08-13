package com.borjabares.pan_ssh.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.borjabares.pan_ssh.util.FriendlyTitleGenerator;

public class FriendlyTitleGeneratorTest {

	@Test
	public void generate1(){
		String input = "Esta es uña Prueba" ;
		String expected = "esta-prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate2(){
		String input = "eSta.--és#otrÁ+prùEba----" ;
		String expected = "esta-otra-prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate3(){
		String input = "(iÑo,prueba,más+--" ;
		String expected = "prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate4(){
		String input = "esta-una-dos-una" ;
		String expected = "esta";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate5(){
		String input = "el-una-dos-y-prueba" ;
		String expected = "prueba";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
	@Test
	public void generate6(){
		String input = "a-e-i-o-u" ;
		String expected = "a-e-i-o-u";
		String output;
		
		output = FriendlyTitleGenerator.generate(input);
		
		assertEquals(output, expected);
	}
	
}
