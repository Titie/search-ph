package com.search.crawler.utils;

import java.util.regex.Pattern;

public class StringUtility {
	/**
	 * Work similar as String.replaceAll() except that this method is case-insensitive
	 * @param string
	 * @param regex
	 * @param replaceWith
	 * @return
	 */
	public static String replaceAll(String string, String regex, String replaceWith){
		Pattern myPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		/*for space,new line, tab checks*/
		//Pattern myPattern = Pattern.compile(regex+"[ /n/t/r]", Pattern.CASE_INSENSITIVE);
		string = myPattern.matcher(string).replaceAll(replaceWith);
		return string;
	}

}
