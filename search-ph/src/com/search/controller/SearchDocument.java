package com.search.controller;

import java.util.ArrayList;
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
	
	
	public static void main(String[] args) {
		long currentTime1 = System.currentTimeMillis();
		List<Document> documents 		= Utils.loadDataFromDB();
		System.out.println("TIME LOAD DATA = " + (System.currentTimeMillis() - currentTime1));
		
		SearchDocument searchDocument 	= new SearchDocument(documents);
		long currentTime2 = System.currentTimeMillis();
		TFIDF tfidf 					= new TFIDF(documents);
		tfidf.processDocumentsAndCalculateTFIDF();
		System.out.println("TIME TO PROCESS DATA = " + (System.currentTimeMillis() - currentTime2));
		
		String query 					= "Máy tính xách tay tosiba được công bố năm 2013";
		List<Document> retrieval 		= searchDocument.searchDocument(query);
		
		System.out.println("search result: " + retrieval);
	}

	
	
	public List<Document> searchDocument(String query) {
		Document document 					= new Document(0L, "Query", query, "--This is query--");
		
		List<Document> documentRetrieval 	= new ArrayList<Document>();
		
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
