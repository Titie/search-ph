package com.search.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.search.model.Document;
import com.search.tfidf.TFIDF;
import com.search.utils.Utils;


/**
 * This method will return data
 * @author AnhH1
 *
 */
public class SearchDocument {
	private List<Document> documents = new ArrayList<Document>();
	
//	public static void main(String[] args) {
//		System.out.println("Use CTRL+C to quite to program.");
//
//		// Create the reader for reading in the text typed in the console. 
//		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//		try {
//		  String line = null;
//		  while ((line = bufferedReader.readLine()).length() > 0) {
//		    for (int index = 0; index < line.length(); index++) {
//
//		      // Convert the integer to a hexadecimal code.
//		      String hexCode = Integer.toHexString(line.codePointAt(index)).toUpperCase();
//
//
//		      // but the it must be a four number value.
//		      String hexCodeWithAllLeadingZeros = "0000" + hexCode;
//		      String hexCodeWithLeadingZeros = hexCodeWithAllLeadingZeros.substring(hexCodeWithAllLeadingZeros.length()-4);
//
//		      System.out.println("\\u" + hexCodeWithLeadingZeros);
//		      PrintStream out = new PrintStream(System.out, true, "UTF-8");
//			    out.println(hexCodeWithLeadingZeros);
//		    }
//		    
//		  }
//		 
//		} catch (IOException ioException) {
//		       ioException.printStackTrace();
//		  }
//		 }
	public static void main(String[] args) throws IOException {
		long currentTime1 				= System.currentTimeMillis();
		List<Document> documents 		= Utils.loadDataFromDB();
		System.out.println("TIME LOAD DATA = " + (System.currentTimeMillis() - currentTime1));
		
		long currentTime2 				= System.currentTimeMillis();
		
		System.out.println("TIME SAVE DATA = " + (System.currentTimeMillis() - currentTime2));
		SearchDocument searchDocument 	= new SearchDocument(documents);
//		
//		long currentTime2 				= System.currentTimeMillis();
//		
		TFIDF tfidf 					= new TFIDF(documents);
		tfidf.processDocumentsAndCalculateTFIDF();
		
		Utils.saveWordInToDataBase(tfidf.getDocuments());
		Utils.saveTFIDFWordInToDataBase(tfidf.getWords());
		
		System.out.println("TIME TO PROCESS DATA = " + (System.currentTimeMillis() - currentTime2));
//		
//		String query 					= "Toshiba giới thiệu máy Satellite";
//		List<Document> retrieval 		= new ArrayList<Document>();
//		retrieval 						= searchDocument.searchDocument(query, tfidf);
//		System.out.println("search result: \n" + retrieval);
//		System.out.println(tfidf.getWords());
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
//		boolean isContinue = true;
//		while (isContinue) {
//			System.out.println("WRITE YOUR QUERY.....");
//			query = in.readLine();
//			byte[] unicodeBytes = query.getBytes("UTF-8");
//			System.out.println(new String(unicodeBytes, "UTF-8"));
//			if (query.equalsIgnoreCase("exit")) {
//				System.out.println("System exit ----- ");
//				isContinue = false;
//			} else {
//				System.out.println("Query inputed:" + query);
//				System.out.println("Search .................");
//				retrieval 		= searchDocument.searchDocument(query, tfidf);
//				System.out.println("search result: \n" + retrieval.subList(0, 10));
//			}
//		}
	}

	
	
	public List<Document> searchDocument(String query, TFIDF tfidf) {
		Document queryD 					= new Document(0L, "Query", query, "--This is query--");
		tfidf.setQuery(queryD);
		List<Document> documentRetrieval 	= new ArrayList<Document>();
		documentRetrieval = tfidf.proccessCalculateVectorSpaceModel();
		Collections.sort(documentRetrieval);
		return documentRetrieval;
	}
	
	
	
	
	public SearchDocument(List<Document> documents) {
		this.documents = documents;
	}



	/* ******************** SETTERs AND GETTERs ********************** */
	
	/**
	 * @return the documents
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
