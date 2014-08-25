package com.search.tfidf;

public class Word {
	private Long 	wordId;
	private String 	word;
	private String 	typeWord;
	private Long 	documentId;
	private Integer occurence;
	
	
	public Word(Long wordId, String word, String typeWord, Long documentId, Integer occurence) {
		super();
		this.wordId = wordId;
		this.word = word;
		this.typeWord = typeWord;
		this.documentId = documentId;
		this.occurence = occurence;
	}
	
	
	@Override
	public int hashCode() {
		return word.hashCode();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Word) {
			Word word = (Word)obj;
			if (word.getWord().equalsIgnoreCase(this.word) && word.getTypeWord().equalsIgnoreCase(this.typeWord)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void increaseOccurence() {
		occurence++;
	}
	
	@Override
	public String toString() {
		return "Word:" + word + " --- type of word:" + typeWord + " --- occurence:" + occurence + "\n";
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
	 * @return the occurence
	 */
	public Integer getOccurence() {
		return occurence;
	}
	/**
	 * @param occurence the occurence to set
	 */
	public void setOccurence(Integer occurence) {
		this.occurence = occurence;
	}
	
	
}
