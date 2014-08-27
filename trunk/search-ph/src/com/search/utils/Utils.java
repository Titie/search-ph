package com.search.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.search.model.Document;
import com.search.model.Word;

import edu.stanford.nlp.ling.WordTag;


/**
 * 
 * @author AnhH1
 *
 */
public class Utils {
	
	
	/**
	 * This method is used to test some method in this class.
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * This method reads data in file and returns its.
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		try {
			File f = new File(filePath);
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			return "";
		}
	}
	
	/**
	 * This method will read data in each line and add its value into list.
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileByLine(String filePath) {
		List<String> dataInLines = new ArrayList<String>();
		try {
			File f					= new File(filePath);
			BufferedReader reader 	= new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				dataInLines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Has exception when read data in file : " + filePath);
		}
		
		return dataInLines;
	}
	
	/**
	 * This method will read data in each line and add its value into list.
	 * @param filePath
	 * @return
	 */
	public static List<Word> readWordInFile(String filePath) {
		List<Word> dataInLines = new ArrayList<Word>();
		try {
			File f					= new File(filePath);
			BufferedReader reader 	= new BufferedReader(new FileReader(f));
			String line;
			WordTag wordTag = new WordTag();
			while((line = reader.readLine()) != null) {
				wordTag.setWord(line);
				Word word = new Word(null, line, null, null, 1);
				dataInLines.add(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Has exception when read data in file : " + filePath);
		}
		
		return dataInLines;
	}
	
	
	/**
	 * This method will return list file in the folder.
	 * @param folder
	 * @return
	 */
	public static List<String> getListFileFromFolder(String folderPath) {
		List<String> listFile = new ArrayList<String>();
		try {
			File folder = new File(folderPath);
			if (folder.isDirectory()) {
				return Arrays.asList(folder.list());
			}
		} catch (Exception e) {
			System.out.println("Has exception when read folder: " + folderPath);
		}
		return listFile;
	}
	
	
	/**
	 * This method is used to load data from database.
	 */
	public static List<Document> loadDataFromDB() {
		List<Document> documents = new ArrayList<Document>();
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			String sql = "SELECT id, url, title, content from Content where id < 500";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				Long id = rs.getLong("id");
				String url = rs.getString("url");
				String title = rs.getString("title");
				String content = rs.getString("content");
				//System.out.println("id = " + id + "--- title = " + title);
				try {
					Document document = new Document(id, title, content, url);
					documents.add(document);
				} catch(Exception e) {
					//System.out.println("Has exception e --- ");
				}
				
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		
		System.out.println("LOAD DATA FROM DATABSE IS SUCCESSFUL WITH DATA SIZE = " + documents.size() + " document ( " + documents.toString().getBytes().length + "bytes)" );
		return documents;
	}
}
