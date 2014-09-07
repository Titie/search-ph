package com.search.model;



/**
 * 
 * @author AnhH1
 *
 */

public class Word implements Comparable<Word>{
	private Long 	wordId;
	private String 	word;
	private String 	typeWord;
	private Long 	documentId;
	private Double tf		= 0D;
	private Double idf 		= 0D;
	private Double tfidf 	= 0D;
	private Double processTF= 0D;
	public Word(Long wordId, String word, String typeWord, Long documentId, Double tf) {
		super();
		this.wordId 	= wordId;
		this.word 		= word;
		this.typeWord 	= typeWord;
		this.documentId = documentId;
		this.tf 		= tf;
	}
	
	
	@Override
	public int hashCode() {
		return word.hashCode();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Word) {
			Word word = (Word)obj;
			if (word.getWord().trim().equalsIgnoreCase(this.word)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void increaseOccurence() {
		tf++;
	}
	
	@Override
	public String toString() {
		return "Word:" + word + " ---type of word:" + typeWord + " ---TF:" + tf + "-----TF-IDF:" + getTfidf() +"\n";
	}
	
	@Override
	public int compareTo(Word arg0) {
		if (arg0.getTfidf() > getTfidf()) return 1;
		if (arg0.getTfidf() < getTfidf()) return -1;
		return 0;
	}
	
	/* ********************* SETTER AND GETTER **************************** */
	
	/**
	 * @return the wordId
	 */
	public Long getWordId() {
		return wordId;
	}
	/**
	 * @param wordId the wordId to set
	 */
	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}
	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * @return the typeWord
	 */
	public String getTypeWord() {
		return typeWord;
	}
	/**
	 * @param typeWord the typeWord to set
	 */
	public void setTypeWord(String typeWord) {
		this.typeWord = typeWord;
	}
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
	 * @return the tf
	 */
	public Double getTf() {
		return tf;
	}


	/**
	 * @param tf the tf to set
	 */
	public void setTf(Double tf) {
		this.tf = tf;
	}


	/**
	 * @return the idf
	 */
	public Double getIdf() {
		return idf;
	}


	/**
	 * @param idf the idf to set
	 */
	public void setIdf(Double idf) {
		this.idf = idf;
	}


	/**
	 * @return the tfidf
	 */
	public Double getTfidf() {
		return getProcessTF()*getIdf();
	}


	/**
	 * @param tfidf the tfidf to set
	 */
	public void setTfidf(Double tfidf) {
		this.tfidf = tfidf;
	}


	/**
	 * @return the processTF
	 */
	public Double getProcessTF() {
		return processTF;
	}


	/**
	 * @param processTF the processTF to set
	 */
	public void setProcessTF(Double processTF) {
		this.processTF = processTF;
	}

}
