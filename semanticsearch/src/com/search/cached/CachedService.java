package com.search.cached;

import java.util.ArrayList;
import java.util.List;

import com.search.model.Document;
import com.search.utils.Utils;

/**
 * 
 * @author AnhH1
 *
 */
public class CachedService {
	
	public static void main(String[] args) {
		saveDocumentIntoMemcached();
	}
	
	public static List<Document> getDocumentListFromMemCached() {
		System.out.println("===========GET DOCUMENT LIST FROM MEMCACHED=======");
		List<Document> documents = new ArrayList<Document>();
		int total = (int)SemantichSearchCached.getInstance().get("TOTALDOCUMENT");
		for (int i = 0; i < total; i++) {
			Document d = (Document)SemantichSearchCached.getInstance().get("doc:" + i);
			documents.add(d);
		}
		return documents;
	}
	
	public static void saveDocumentIntoMemcached() {
		List<Document> documents 	= Utils.loadDataFromDB();
		for (int i = 0; i < documents.size(); i++) {
			System.out.println("SAVE DOCUMENT ID = " + i + " INTO THE MEMCACHED");
			SemantichSearchCached.getInstance().set("doc:" + i, documents.get(i));
		}
		SemantichSearchCached.getInstance().set("TOTALDOCUMENT", documents.size());
	}

}
