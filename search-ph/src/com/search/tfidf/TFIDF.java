package com.search.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TFIDF {
	private List<Document> 	documents;
	private Integer 		totalDocument;
	
	
	public static void main(String[] args) {
		String file = "C:\\Users\\anhh1\\Desktop\\file1.txt";
		String dataInFile = readFile(file);
		
		Document document = new Document();
		document.setDocumentData(dataInFile);
		document.loadWordsFromData();
		
		System.out.println(document.getWords());
	}
	
	
	
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
	
	/* ********************** SETTER AND GETTER ************************ */
	
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

