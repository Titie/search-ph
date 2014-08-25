package com.search.tfidf;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.WordTag;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class Document {
	private Long 	documentId;
	private String 	documentName;
	private String 	documentData;
	private List<Word> words = new ArrayList<>();
	
	
	
	/**
	 * This method load word list from data.
	 */
	public void loadWordsFromData() {
		if (documentData == null || documentData == "") {
			System.out.println("");
		} else {
			VietnameseMaxentTagger vietnameseMaxentTagger = new VietnameseMaxentTagger();
			List<WordTag> wordTags = vietnameseMaxentTagger.tagText2(documentData);
			for (WordTag wordTag : wordTags) {
				Word word = new Word(null, wordTag.value(), wordTag.tag(), documentId, 1);
				addWordIntoWords(word);
			}
		}
	}
	
	/**
	 * This method add word into word list.
	 * @param word
	 */
	private void addWordIntoWords(Word word) {
		int index = words.indexOf(word);
		if(index > 0) {
			words.get(index).increaseOccurence();
		} else {
			words.add(word);
		}
	}
	
	
	/* ********************* SETTER AND GETTER **************************** */
	
	/**
	 * @return the documentId
	 */
	public Long getDocumentId() {
		return documentId;
	}
	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}
	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	/**
	 * @return the documentData
	 */
	public String getDocumentData() {
		return documentData;
	}
	/**
	 * @param documentData the documentData to set
	 */
	public void setDocumentData(String documentData) {
		this.documentData = documentData;
	}
	/**
	 * @return the words
	 */
	public List<Word> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(List<Word> words) {
		this.words = words;
	}
	
}
