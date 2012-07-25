package com.borjabares.pan_ssh.util;

public class Trimmer {

	private static char[] excluded = { ' ', '\'', '\\', '\"' };

	public static String trim(String string) {
		string = string.trim();
		int index;
		for (int i = 0; i < excluded.length; i++) {
			index = string.indexOf(excluded[i]);
			while (index!=-1){
				if (index!=0 && index+1 != string.length()){
					string = string.substring(0,index) + string.substring(index+1,string.length());
				} else {
					if (index ==0){
						string = string.substring(1);
					} else {
						string = string.substring(0,index);
					}
				}
				index = string.indexOf(excluded[i]);
			}
		}
		return string;
	}
	
}
