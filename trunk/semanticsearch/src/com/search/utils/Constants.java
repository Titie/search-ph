package com.search.utils;


public class Constants {
	
	public static final String STOP_WORDS 			= ".\\resources\\stopword\\vn-stopwords.data";
	
	public static final String JDBC_DRIVER 			= ConfigurationUtils.get("db.driver");
	public static final String DB_URL 				= ConfigurationUtils.get("db.url");
	public static final String USER 				= ConfigurationUtils.get("db.username");
	public static final String PASS 				= ConfigurationUtils.get("db.password");
	public static final Integer TOTAL_DOCUMENT		= Integer.parseInt(ConfigurationUtils.get("search.totaldocument"));
	public static final String SEARCH_CONFIG 		= ConfigurationUtils.get("search.sematicsearchconfig");
	
	// Constant for memcached;
	public static String HOST 						= "HOST";
	public static String WEIGHTS 					= "WEIGHTS";
	public static String INIT_CONN 					= "INIT_CONN";
	public static String MIN_CONN 					= "MIN_CONN";
	public static String MAX_CONN 					= "MAX_CONN";
	public static String MAINT_SLEEP 				= "MAINT_SLEEP";
	public static String NAGLE 						= "NAGLE";
	public static String MAX_IDLE 					= "MAX_IDLE";
	public static String SOCKET_TO 					= "SOCKET_TO";
	public static String SOCKET_CONNECT_TO 			= "SOCKET_CONNECT_TO";
	public static Boolean SEMANTICSEARCH 			= false;
	
	public static final String KEY_DOC_SM 			= "doc_sm";
	public static final String KEY_DOC_NSM 			= "doc_nsm";
	public static final String KEY_TOTAL_DOCUMENT_SM 	= "totaldocument_sm";
	public static final String KEY_TOTAL_DOCUMENT_NSM 	= "totaldocument_nsm";
}
