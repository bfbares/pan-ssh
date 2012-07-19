package com.borjabares.pan_ssh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCodificator {

	public static String codify(String password) throws NoSuchAlgorithmException {

		byte codified[];
		MessageDigest mda = MessageDigest.getInstance("SHA-512");

		password = salting(password);
		mda.reset();
		mda.update(password.getBytes());
		codified = mda.digest();		
		password = toHex(codified);

		return password;
	}

	private static String salting(String password) {
		int sumNumb = 0;
		int lowercaseNumber = 0;
		int uppercaseChar = 0;
		char selectedChar;

		for (int i = 0; i < password.length(); i++) {
			selectedChar = password.charAt(i);
			if (Character.isDigit(selectedChar)) {
				//Sum of the numbers
				sumNumb += Integer.parseInt(Character.toString(selectedChar));
			} else {
				if (Character.isLowerCase(selectedChar)) {
					//Number of lowercase characters
					lowercaseNumber++;
				} else {
					//Sum of the ASCII code of uppercase characters
					//Converting from UNICODE to ASCII adding 55
					uppercaseChar += Character.getNumericValue(selectedChar) + 55; 
					uppercaseChar = uppercaseChar % 122;
					if (uppercaseChar < 65){
						uppercaseChar = 65;
					}
					if (uppercaseChar > 90 && uppercaseChar < 97) {
						uppercaseChar = 97;
					}
				}
			}
		}
		password = sumNumb + password + lowercaseNumber + (char) uppercaseChar;

		return password;
	}

	private static String toHex(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}
}
