package com.borjabares.pan_ssh.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class FriendlyTitleGenerator {

	public static final Pattern DIACRITICS_AND_FRIENDS = Pattern
			.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

	public static String stripDiacritics(String str) {
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

		while (toRet.lastIndexOf("-") == toRet.length() - 1) {
			toRet = toRet.substring(0, toRet.length() - 1);
		}

		while (toRet.indexOf("-") == 0) {
			toRet = toRet.substring(1, toRet.length());
		}
		
		String toRetBC = toRet; 

		for (int i = 0; i < toRet.length(); i++) {
			int indexOf = toRet.indexOf("-",i);
			if (indexOf == -1) {
				if (toRet.length() - i <= 3 && i > 3) {
					toRet = toRet.substring(0, i-1);
				}
				break;
			}
			if (indexOf - i <= 3) {
				if (i>3){
					toRet = toRet.substring(0, i) + toRet.substring(indexOf+1, toRet.length());
				} else {
					toRet = toRet.substring(indexOf+1, toRet.length());
				}
				i--;
			} else {
				i = indexOf;
			}
		}
		
		if (toRet.length()<=3){
			toRet = toRetBC;
		}

		return toRet;
	}

}