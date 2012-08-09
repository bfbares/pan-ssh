package com.borjabares.pan_ssh.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class FriendlyTitleGenerator {
	
	public static final Pattern DIACRITICS_AND_FRIENDS 
    = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");


	private static String stripDiacritics(String str) {
	    str = Normalizer.normalize(str, Normalizer.Form.NFD);
	    str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
	    return str;
	}

	public static String generate(String str) {
		
		String toRet = "";
		boolean previousSlash = false;
		
		str = str.trim();

		str = stripDiacritics(str);
		
		str = str.toLowerCase();

		for (int i = 0; i < str.length(); i++) {
			char charAt = str.charAt(i);
			if (Character.isLetterOrDigit(charAt)) {
				toRet = toRet.concat(Character.toString(charAt));
				previousSlash = false;
			} else {
				if (!previousSlash) {
					toRet = toRet.concat("-");
					previousSlash = true;
				}
			}
		}

		while(toRet.lastIndexOf("-") == toRet.length()-1){
			toRet = toRet.substring(0, toRet.length()-1);
		}
		
		return toRet;
	}

}