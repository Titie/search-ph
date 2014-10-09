package com.search.utils;

public class Constants {
	
	public static final String STOP_WORDS 			= "..\\search-ph\\resources\\stopword\\vn-stopwords.data";
	public static final String JDBC_DRIVER 			= ConfigurationUtils.getInstance().get("db.driver");
	public static final String DB_URL 				= ConfigurationUtils.getInstance().get("db.url");
	public static final String USER 				= ConfigurationUtils.getInstance().get("db.username");
	public static final String PASS 				= ConfigurationUtils.getInstance().get("db.password");
	public static final Integer TOTAL_DOCUMENT		= Integer.parseInt(ConfigurationUtils.getInstance().get("search.totaldocument"));
	public static final Boolean SEMANTICSEARCH 		= Boolean.valueOf(ConfigurationUtils.getInstance().get("search.sematicsearch"));
}
