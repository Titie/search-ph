package com.search.cached;

import com.search.model.Document;

public class TestMemCached {
	
	public static void main(String[] args) {
		int total = (int)SemantichSearchCached.getInstance().get("TOTALDOCUMENT");
		for (int i = 0; i < total; i++) {
			@SuppressWarnings("unused")
			Document d = (Document)SemantichSearchCached.getInstance().get("doc:" + i);
		}
	}

}
