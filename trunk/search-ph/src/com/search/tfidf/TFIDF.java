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
	private Document query;
	
	
	
	/**
	 * This is constructor for documents
	 * @param documents
	 */
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
				Word w = new Word(0L, word.getWord(), word.getTypeWord(), 0L, 1D);
				addWordIntoWords(w);
			}
		}
		
		
		//update IDF for each word in each document
		for (Document document : documents) {
			//calculate IDF for word in the all document.
			for (Word word : document.getWords()) {
				int wordId = words.indexOf(word);
				double idfWord = Math.log(documents.size()/words.get(wordId).getTf());
				
				//set idf for word in each document
				word.setIdf(idfWord);
				
				words.get(wordId).setIdf(idfWord);
			}
			Collections.sort(document.getWords());
			
			if (document.getWords().size() > 10) {
				//System.out.println(document.getWords().subList(0, 10));
			}
		}
		//System.out.println("word in the all document: " + words);
	}
	
	
	/**
	 * This method will remove all stop words in the document.
	 * @param stopwords
	 */
	public void processQuery() {
		System.out.println("------ REMOVE STOP WORDS FOR QUERY ------ ");
		query.getWords().removeAll(stopwords);
	}
	
	
	
	public List<Document> proccessCalculateVectorSpaceModel() {
		processQuery();
		List<Word> queryWords = query.getWords();
		Collections.sort(queryWords);
		double maximumFrequency = queryWords.get(0).getTf();
		
		//calculate TF-IDF for query
		for (Word wordQ : queryWords) {
			
			//check ton tai gia tri cua word(Q) trong word(allDocument)
			int index = words.indexOf(wordQ);
			//System.out.println("INDEX ========== " + index);
			if (index > -1) {
				wordQ.setIdf(words.get(index).getIdf());
				wordQ.setTf(wordQ.getTf()/maximumFrequency);
				//System.out.println("IDF: " + words.get(index).getIdf());
			}
		}
		
		System.out.println("Query:" + queryWords);
		for (Document document : documents) {
			//calculate document with query
			double cosinQD = 0D;
			for (Word wordInQuery : query.getWords()) {
				
				//tim word cua query trong document.
				int idWordInD = document.getWords().indexOf(wordInQuery);
				if (idWordInD > -1) {
					cosinQD += wordInQuery.getTfidf()*document.getWords().get(idWordInD).getTfidf();
				}
			}
			document.setCosinWithQuery(cosinQD/(document.getDocumentLeng()*query.getDocumentLeng()));
		}
		
		return documents;
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


	/**
	 * @return the query
	 */
	public Document getQuery() {
		return query;
	}


	/**
	 * @param query the query to set
	 */
	public void setQuery(Document query) {
		this.query = query;
	}
}
