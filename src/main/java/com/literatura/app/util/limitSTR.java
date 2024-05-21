package com.literatura.app.util;

public class limitSTR {
	public static String limitLen(String str, int len) {
		return (str.length() <= len) ? str : str.substring(0, len);
	}
}
