package com.search.tfidf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.search.model.Document;
import com.search.model.Word;
import com.search.utils.Constants;
import com.search.utils.Utils;

public class TFIDF {
	private List<Document> 	documents;
	private List<Word> stopwords 		= Utils.readWordInFile(Constants.STOP_WORDS);
	private List<Word> 		words		= new ArrayList<Word>();
	
	public TFIDF(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * This method will remove all stop words in all documents.
	 * @param totalDocument ( = 0 -> use default value of total document)
	 */
	public void processDocumentsAndCalculateTFIDF() {
		System.out.println("------ REMOVE STOP WORDS FOR ALL DOCUMENT ------ ");
		for (Document document : documents) {
			document.getWords().removeAll(stopwords);
			//calculate IDF for word in the all document.
			for (Word word : document.getWords()) {
				Word w = new Word(0L, word.getWord(), word.getTypeWord(), 0L, 1);
				addWordIntoWords(w);
			}
		}
		
		
		//update IDF for each word in each document
		for (Document document : documents) {
			//calculate IDF for word in the all document.
			for (Word word : document.getWords()) {
				int wordId = words.indexOf(word);
				word.setIdf(Math.log(documents.size()/words.get(wordId).getTf()));
			}
			Collections.sort(document.getWords());
			System.out.println(document.getWords().subList(0, 10));
		}
		//System.out.println("word in the all document: " + words);
	}
	
	
	/**
	 * This method will remove all stop words in the document.
	 * @param stopwords
	 */
	public Document processQuery(Document document) {
		System.out.println("------ REMOVE STOP WORDS FOR QUERY ------ ");
		document.getWords().removeAll(stopwords);
		return document;
	}
	
	/**
	 * This method add word into word list.
	 * @param word
	 */
	public void addWordIntoWords(Word word) {
		int index = words.indexOf(word);
		if(index > 0) {
			words.get(index).increaseOccurence();
		} else {
			words.add(word);
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
	 * @param documents
	 *            the documents to set
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
