package com.borjabares.pan_ssh.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.borjabares.pan_ssh.util.Trimmer;

public class TrimmerTest {
	@Test
	public void testBlank(){
		String test = "   prue   ba   ";
		test = Trimmer.trim(test);
		
		assertEquals(test,"prueba");
	}
	
	@Test
	public void testOthers(){
		String test = "p\\i n\"g ü\'i\\no";
		test = Trimmer.trim(test);
		
		assertEquals(test,"pingüino");
	}
}
