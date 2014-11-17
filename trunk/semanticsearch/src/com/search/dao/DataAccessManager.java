package com.search.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.search.model.Document;
import com.search.utils.Constants;


/**
 * 
 * @author AnhH1
 *
 */

public class DataAccessManager {
	
	
	/**
	 * This method is used to load data from database.
	 */
	public static List<Document> loadDataFromDB(boolean isSemanticSearch) {
		List<Document> documents = new ArrayList<Document>();
		Connection conn = null;
		Statement stmt = null;
		List<String> ids = new ArrayList<String>();
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

			String sql = "SELECT id, url, title, content from document";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				Long id 		= rs.getLong("id");
				String url 		= rs.getString("url");
				String title 	= rs.getString("title");
				String content 	= rs.getString("content");
				try {
					if (isSemanticSearch) {
						Document document = new Document(id, title, content, url, true);
						documents.add(document);
					} else {
						Document document = new Document(id, title, content, url, false);
						documents.add(document);
					}
					
					System.out.println("LOADING DOCUMENT ID = " + id);
				} catch(Exception e) {
					System.out.println("DOCUMENT ID ERROR = " + id);
					ids.add(id + "");
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
		
		System.out.println("LOADING DATA FROM DATABSE IS SUCCESSFUL WITH DATA SIZE = " + documents.size() + " document ( " + documents.toString().getBytes().length/1024 + "bytes)" );
		System.out.println("\n ================ NEED DELETE RECORDS =============== + \n");
		String queryDelete = "DELETE FROM CONTENT WHERE ";
		for (String id : ids) {
			queryDelete += " ID = " + id + " AND ";
		}
		System.out.println("QUERY DELETE: " + queryDelete);
		return documents;
	}

}
