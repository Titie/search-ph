package com.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

import com.search.cached.CachedService;
import com.search.ontology.OntologyManager;

import edu.stanford.nlp.ling.WordTag;


/**
 * 
 * @author AnhH1
 *
 */

public class Document implements Comparable<Document>, Serializable, Cloneable {

	private static final long serialVersionUID = -5136216053114374992L;
	private Long 	id;
	private String 	title;
	private String 	content;
	private String 	url;
	private Double 	cosinWithQuery 		= 0D;
	private Double documentLeng 		= 0D;
	private List<Word> words 			= new ArrayList<>();
	private boolean  isSemanticSearch	= false;
	/**
	 * This method is call when create instance of {@link Document}.
	 * @param id
	 * @param title
	 * @param content
	 * @param url
	 */
	public Document(Long id, String title, String content, String url) {
		this.id = id;
		this.title = title.trim();
		this.content = content;
		this.url = url.replaceAll("//", "/");
		loadWordsFromData();
	}
	
	
	
	public Document(Long id, String title, String content, String url, boolean isSemanticSearch) {
		this.isSemanticSearch 	= isSemanticSearch;
		this.id 				= id;
		this.title 				= title.trim();
		this.content 			= content;
		this.url 				= url.replaceAll("//", "/");
		loadWordsFromData();
	}
	
		
	/**
	 * This method load list words from data.
	 */
	public void loadWordsFromData() {
		if (content == null || content == "") {
			System.out.println("");
		} else {
			VietnameseMaxentTagger vietnameseMaxentTagger = new VietnameseMaxentTagger();
			List<WordTag> wordTags = vietnameseMaxentTagger.tagText2(content);
			if ("Query".equalsIgnoreCase(title)) {
				System.out.println("word in query ===>" + wordTags);
			}
			for (WordTag wordTag : wordTags) {
				String wordTagValue = CachedService.getDictionary().get(wordTag.value().toUpperCase()) == null ? wordTag.value() : CachedService.getDictionary().get(wordTag.value().toUpperCase());
				//System.out.println("WORD: " + wordTag.value());
				if (isSemanticSearch) {
					List<String> semanticEntries = OntologyManager.getSemanticEntriesForTerm(wordTagValue);
					for (String semanticEntry : semanticEntries) {
						Word word = new Word(null, semanticEntry, "SE", id, 1D);
						addWordIntoWords(word);
					}
				} else {
					Word word = new Word(null, wordTagValue.trim(), wordTag.tag(), id, 1D);
					addWordIntoWords(word);
				}
			}
		}
	}
	
	/**
	 * This method add word into word list.
	 * @param word
	 */
	private void addWordIntoWords(Word word) {
		int index = words.indexOf(word);
		if(index > -1) {
			words.get(index).increaseOccurence();
		} else {
			words.add(word);
		}
	}
	
	@Override
	public String toString() {
		return "cosin D-Q: (documentid = " + id + ") " + getCosinWithQuery() + "-----Url: " + url + "\n";
	}
	
	/**
	 * This method is used to clone object.
	 */
	public Document clone() {
		try {
			return (Document) super.clone();
		} catch(Exception CloneNotSupportedException) {
			return new Document(1L, "Has exception e----", "", "");
		}
	}
	
	
	/* ********************* SETTER AND GETTER **************************** */
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the cosinWithQuery
	 */
	public Double getCosinWithQuery() {
		return cosinWithQuery;
	}

	/**
	 * @param cosinWithQuery the cosinWithQuery to set
	 */
	public void setCosinWithQuery(Double cosinWithQuery) {
		this.cosinWithQuery = cosinWithQuery;
	}

	@Override
	public int compareTo(Document o) {
		if (o.getCosinWithQuery() > getCosinWithQuery()) return 1;
		if (o.getCosinWithQuery() < getCosinWithQuery()) return -1;
		return 0;
	}

	/**
	 * @return the documentLeng
	 */
	public Double getDocumentLeng() {
		documentLeng = 0D;
		for (Word word : words) {
			documentLeng += word.getTfidf()*word.getTfidf();
		}
		return Math.sqrt(documentLeng);
	}

	/**
	 * @param documentLeng the documentLeng to set
	 */
	public void setDocumentLeng(Double documentLeng) {
		this.documentLeng = documentLeng;
	}

	/**
	 * @return the isSemanticSearch
	 */
	public boolean isSemanticSearch() {
		return isSemanticSearch;
	}

	/**
	 * @param isSemanticSearch the isSemanticSearch to set
	 */
	public void setSemanticSearch(boolean isSemanticSearch) {
		this.isSemanticSearch = isSemanticSearch;
	}
	
}
