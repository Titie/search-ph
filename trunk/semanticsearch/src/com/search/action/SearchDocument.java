package com.search.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.search.model.Document;
import com.search.tfidf.TFIDF;


/**
 * This class is used to search data.
 * @author AnhH1
 *
 */
public class SearchDocument {
	
	public List<Document> searchDocument(String query, TFIDF tfidf, boolean isSemanticSearch) {
		Document queryD 					= new Document(0L, "Query", query, "--This is query--", isSemanticSearch);
		tfidf.setQuery(queryD);
		List<Document> documentRetrieval 	= new ArrayList<Document>();
		documentRetrieval = tfidf.proccessCalculateVectorSpaceModel();
		Collections.sort(documentRetrieval);
		for (int i = 1; i <= documentRetrieval.size(); i++) {
			documentRetrieval.get(i-1).setTitle(i + " - " + documentRetrieval.get(i-1).getTitle());
		}
		return documentRetrieval;
	}
}
