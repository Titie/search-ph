package com.search.model;

import java.util.ArrayList;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;


/**
 * 
 * @author AnhH1
 *
 */

public class Document {
	private Long 	id;
	private String 	title;
	private String 	content;
	private String 	url;
	private List<Word> words = new ArrayList<>();
	
	/**
	 * This method is call when create instance of {@link Document}.
	 * @param id
	 * @param title
	 * @param content
	 * @param url
	 */
	public Document(Long id, String title, String content, String url) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.url = url;
		loadWordsFromData();
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
				Word word = new Word(null, wordTag.value().trim(), wordTag.tag(), id, 1);
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
	
}
