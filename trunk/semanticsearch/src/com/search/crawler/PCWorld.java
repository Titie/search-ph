package com.search.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import com.search.crawler.utils.HTMLParser;

public class PCWorld {
    public static String proxyHost 		= "fsoft-proxy";
    public static String proxyPort 		= "8080";
    public static String proxyUser 		= "anhh1";
    public static String proxyPassword 	= "m4steam1@";
    public static Map<String, String> urlContents 	= new HashMap<String, String>();
    public static Set<String> urls 		= new HashSet<String>();
    public static String baseURL 		= "http://www.pcworld.com.vn/articles/san-pham/may-xach-tay/";
	public static void main(String[] args) {
		try {
			File f = new File("D:\\urls_mtxt.txt");
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println("CALL " + line + " --> INSERT DATA");
				getDataContentFromUrl(line);
			}
			reader.close();
		} catch (IOException e) {}
	}
	
	
	/**
	 * This method is used to get all url form root URL.
	 * @param rootURL
	 * @return
	 */
	public static void genrateUrlBaseOnDate(String rootURL) {
		Set<String> urls = new HashSet<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calFrom 	= Calendar.getInstance();
		Calendar calTo 		= Calendar.getInstance();
		calFrom.set(2005, 1, 1);
		calTo.set(2014, 8, 30);
		for (Date date = calFrom.getTime(); date.compareTo(calTo.getTime()) < 0;date = calFrom.getTime()) {
			String dateQuery = URLEncoder.encode(dateFormat.format(date));
			String url = rootURL + dateQuery;
			getURLsFromUrl(url);
			calFrom.add(Calendar.DAY_OF_YEAR, 5);
		}
	}
	
	
	/**
	 * This method is used to get all url into the parent url.
	 * @param url
	 * @return
	 */
	public static void getURLsFromUrl(String url) {
		
		HTMLParser parser = new HTMLParser();
		parser.loadURL(url);
		parser.filter("a", "class:title");
		for(int i = 1; i < parser.getNumberOfNodes(); i++ ) {
			String urlSub = parser.getNodeAt(i).getAttribute("href");
			urls.add(baseURL + urlSub);
		}
		
		System.out.println("TOTAL URL IS GOT AFTER CALLING URL: " + url + " IS = " + urls.size());
	}
	
	/**
	 * This method is used to get data from url and insert them into the database.
	 * @param subUrl
	 * @throws Exception
	 */
	public static void getDataContentFromUrl(String subUrl) {
		HTMLParser parser 	= new HTMLParser();
		parser.loadURL(subUrl);
		String data 		= parser.getHtml();
		
		parser.setHtml(data);
		parser.filter("div", "class:title");
		String title = StringEscapeUtils.unescapeHtml4 (parser.getNodeAt(0).getHtml().replaceAll("\\<.*?>",""));
		
		parser.setHtml(data);
		parser.filter("div", "id:ar-content-html");
		String content = StringEscapeUtils.unescapeHtml4(parser.getNodeAt(0).getHtml().replaceAll("\\<.*?>",""));
		
		insertDataIntoDB(title.replaceAll("'", ""), subUrl.replaceAll("'", ""), content.replaceAll("'", ""));
	}
	
	
	/*
	 * insert data into table song
	 * 
	 */
	public static void insertDataIntoDB(String title, String url, String content) {
		try {
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver");
			String db = "jdbc:mysql://localhost:3306/searchdata?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
			String user = "root";
			String password = "123456";
			connection = DriverManager.getConnection(db, user, password);
			Statement stmt = connection.createStatement();
			String sqlInsertComposer = "INSERT INTO document ( url, title, content ) VALUES ('"+url.trim()+"','"+title.trim()+"','"+content.trim()+"')";
			stmt.execute(sqlInsertComposer);
		} catch(Exception e) {
			System.out.println("Title: " + title);
			System.out.println(e);
		}
	}
	
	
	
	
    /**
     * the method is used to check and config proxy
     */
    public static void checkProxy() {
        if (null != proxyHost && proxyHost.trim().length() > 0
                && null != proxyPort && proxyPort.trim().length() > 0) {
            System.getProperties().put("proxySet", "true");
            System.getProperties().put("proxyHost", proxyHost);
            System.getProperties().put("proxyPort", proxyPort);

            if (null != proxyUser && proxyUser.trim().length() > 0
                    && proxyPassword != null
                    && proxyPassword.trim().length() > 0) {
                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(proxyUser,
                                proxyPassword.toCharArray());
                    }
                });
            }
        }
    }
    
    public static void writeDataInFile(String file, List<String> urls) {
    	File f = new File(file);
    	try {
    		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String url : urls) {
				bw.write(url + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
