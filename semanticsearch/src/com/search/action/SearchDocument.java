package com.search.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.search.model.Document;
import com.search.model.Word;
import com.search.tfidf.TFIDF;
import com.search.utils.Utils;


/**
 * This class is used to search data.
 * @author AnhH1
 *
 */
public class SearchDocument {


	public static void main(String[] args) throws IOException {
		long currentTime1 				= System.currentTimeMillis();
		List<Document> documents 		= Utils.loadDataFromDB();
		System.out.println("TIME LOAD DATA = " + (System.currentTimeMillis() - currentTime1));
		
		long currentTime2 				= System.currentTimeMillis();
		
		System.out.println("TIME SAVE DATA = " + (System.currentTimeMillis() - currentTime2));
		SearchDocument searchDocument 	= new SearchDocument();
//		
//		long currentTime2 				= System.currentTimeMillis();
//		
		TFIDF tfidf 					= new TFIDF(documents);
		tfidf.processDocumentsAndCalculateTFIDF();
		
//		Utils.saveWordInToDataBase(tfidf.getDocuments());
//		Utils.saveTFIDFWordInToDataBase(tfidf.getWords());
		
		System.out.println("PROCESSING DATA = " + (System.currentTimeMillis() - currentTime2));
//		
		List<Word> words = tfidf.getWords();
		Collections.sort(words);

		File f = new File("D:\\words.txt");
		FileWriter fileWriter = new FileWriter(f,true);
		for (Word w : words) {
			fileWriter.write(w.toString());
		}
		fileWriter.close();

		String query 					= "Toshiba giới thiệu máy Satellite";
		List<Document> retrievals 		= new ArrayList<Document>();
		retrievals 						= searchDocument.searchDocument(query, tfidf);
		System.out.println("search results: \n" + retrievals);
		System.out.println(tfidf.getWords());
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		boolean isContinue = true;
		while (isContinue) {
			System.out.println("TYPE YOUR QUERY.....");
			query = in.readLine();
			byte[] unicodeBytes = query.getBytes("UTF-8");
			System.out.println(new String(unicodeBytes, "UTF-8"));
			if (query.equalsIgnoreCase("exit")) {
				System.out.println("System exit ----- ");
				isContinue = false;
			} else {
				System.out.println("Query:" + query);
				System.out.println("Search .................");
				retrievals 		= searchDocument.searchDocument(query, tfidf);
				if (retrievals.size() > 10) {
					System.out.println("search results: \n" + retrievals.subList(0, 20));
				} else {
					System.out.println("search results: \n" + retrievals);
				}
			}
		}
		
	}

	
	
	public List<Document> searchDocument(String query, TFIDF tfidf) {
		Document queryD 					= new Document(0L, "Query", query, "--This is query--");
		tfidf.setQuery(queryD);
		List<Document> documentRetrieval 	= new ArrayList<Document>();
		documentRetrieval = tfidf.proccessCalculateVectorSpaceModel();
		Collections.sort(documentRetrieval);
		for (int i = 0; i < documentRetrieval.size(); i++) {
			documentRetrieval.get(i).setTitle(i + " - " + documentRetrieval.get(i).getTitle());
		}
		return documentRetrieval;
	}
}
