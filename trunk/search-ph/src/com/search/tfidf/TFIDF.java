package com.search.tfidf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.search.model.Document;
import com.search.model.Word;
import com.search.utils.ConfigurationUtils;
import com.search.utils.Utils;

public class TFIDF {
	private List<Document> documents;
	private Integer totalDocument;
	public static final String STOP_WORDS 	= "..\\search-ph\\resources\\stopword\\vn-stopwords.data";
	static final String JDBC_DRIVER 		= ConfigurationUtils.getInstance().get("db.driver");
	static final String DB_URL 				= ConfigurationUtils.getInstance().get("db.url");
	static final String USER 				= ConfigurationUtils.getInstance().get("db.username");
	static final String PASS 				= ConfigurationUtils.getInstance().get("db.password");

	/**
	 * This method will calculate TF-IDF for all words in the documents.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Word> stopwords = Utils.readWordInFile(STOP_WORDS);
		TFIDF tfidf = new TFIDF();
		tfidf.loadDataFromDB();
		tfidf.removeStopWords(stopwords);
		System.out.println(tfidf.getDocuments().size());
	}

	public static void loadDataFromFolder(String folderPath) {

	}

	
	
	/**
	 * This method will remove all stop words in the all documents.
	 * @param stopwords
	 */
	public void removeStopWords(List<Word> stopwords) {
		System.out.println("------ REMOVE STOP WORDS ------ ");
		for (Document document : documents) {
			document.getWords().removeAll(stopwords);
			System.out.println(document.getWords());
		}
	}
	
	/**
	 * This method is used to load data from database.
	 */
	public void loadDataFromDB() {
		documents = new ArrayList<Document>();
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			String sql = "SELECT id, url, title, content from Content where id < 10";
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				Long id = rs.getLong("id");
				String url = rs.getString("url");
				String title = rs.getString("title");
				String content = rs.getString("content");
				System.out.println("id = " + id + "--- title = " + title);
				try {
					Document document = new Document(id, title, content, url);
					documents.add(document);
				} catch(Exception e) {
					System.out.println("Has exception e --- ");
				}
				
			}
			totalDocument = documents.size();
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
	}

	/* ********************** SETTER AND GETTER ************************ */

	/**
	 * @return the documents
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents
	 *            the documents to set
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * @return the totalDocument
	 */
	public Integer getTotalDocument() {
		return totalDocument;
	}

	/**
	 * @param totalDocument
	 *            the totalDocument to set
	 */
	public void setTotalDocument(Integer totalDocument) {
		this.totalDocument = totalDocument;
	}

}
