package com.search.model;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLException;

import com.search.ontology.OntologyManager;
import com.search.utils.Constants;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;


/**
 * 
 * @author AnhH1
 *
 */

public class Document implements Comparable<Document> {
	private Long 	id;
	private String 	title;
	private String 	content;
	private String 	url;
	private Double 	cosinWithQuery = 0D;
	private Double documentLeng = 0D;

	
	private List<Word> words = new ArrayList<>();
	
	/**
	 * This method is call when create instance of {@link Document}.
	 * @param id
	 * @param title
	 * @param content
	 * @param url
	 */
	public Document(Long id, String title, String content, String url) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.url = url;
		loadWordsFromData();
	}
	
	
	/**
	 * This method load word list from data.
	 * @throws  
	 */
	public void loadSematicWordsFromData() {
		if (content == null || content == "") {
			System.out.println("NO DATA TO LOAD.....");
		} else {
			VietnameseMaxentTagger vietnameseMaxentTagger = new VietnameseMaxentTagger();
			List<WordTag> wordTags = vietnameseMaxentTagger.tagText2(content);
			for (WordTag wordTag : wordTags) {
				if (Constants.SEMANTICSEARCH) {
					List<String> semanticEntries = OntologyManager.getSemanticEntriesForTerm(wordTag.value());
					for (String semanticEntry : semanticEntries) {
						Word word = new Word(null, semanticEntry, "SE", id, 1D);
						addWordIntoWords(word);
					}
				} else {
					Word word = new Word(null, wordTag.value().trim(), wordTag.tag(), id, 1D);
					addWordIntoWords(word);
				}
			}
		}
	}
	
	
	
	
	
	
	/**
	 * This method load word list from data.
	 */
	public void loadWordsFromData() {
		if (content == null || content == "") {
			System.out.println("");
		} else {
			VietnameseMaxentTagger vietnameseMaxentTagger = new VietnameseMaxentTagger();
			List<WordTag> wordTags = vietnameseMaxentTagger.tagText2(content);
			for (WordTag wordTag : wordTags) {
				if (Constants.SEMANTICSEARCH) {
					List<String> semanticEntries = OntologyManager.getSemanticEntriesForTerm(wordTag.value());
					for (String semanticEntry : semanticEntries) {
						Word word = new Word(null, semanticEntry, "SE", id, 1D);
						addWordIntoWords(word);
					}
				} else {
					Word word = new Word(null, wordTag.value().trim(), wordTag.tag(), id, 1D);
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
		return "consin D-Q: " + getCosinWithQuery() + "-----Url: " + url + "\n";
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
	
}
