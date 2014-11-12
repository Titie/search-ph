package com.search.cached;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.search.model.Document;
import com.search.utils.Constants;
import com.search.utils.Utils;

/**
 * 
 * @author AnhH1
 *
 */
public class CachedService {
	
	public static void main(String[] args) {
		loadDictionary();
		saveDocumentIntoMemcached();
	}
	
	public static List<Document> getDocumentListFromMemCached(boolean isSematicSearch) {
		System.out.println("===========GET DOCUMENT LIST FROM MEMCACHED=======");
		List<Document> documents = new ArrayList<Document>();
		if (isSematicSearch) {
			int total = (int)SemantichSearchCached.getInstance().get(Constants.KEY_TOTAL_DOCUMENT_SM);
			for (int i = 0; i < total; i++) {
				Document d = (Document)SemantichSearchCached.getInstance().get(Constants.KEY_DOC_SM + i);
				documents.add(d);
			}
		} else {
			int total = (int)SemantichSearchCached.getInstance().get(Constants.KEY_TOTAL_DOCUMENT_NSM);
			for (int i = 0; i < total; i++) {
				Document d = (Document)SemantichSearchCached.getInstance().get(Constants.KEY_DOC_NSM + i);
				documents.add(d);
			}
		}

		return documents;
	}
	
	public static void saveDocumentIntoMemcached() {
		if (Constants.SEARCH_CONFIG.toLowerCase().contains("true")) {
			List<Document> documents 	= Utils.loadDataFromDB(true);
			for (int i = 0; i < documents.size(); i++) {
				System.out.println("SAVE DOCUMENT ID = " + i + " INTO THE MEMCACHED");
				SemantichSearchCached.getInstance().set(Constants.KEY_DOC_SM + i, documents.get(i));
			}
			SemantichSearchCached.getInstance().set(Constants.KEY_TOTAL_DOCUMENT_SM, documents.size());
		}
		
		if (Constants.SEARCH_CONFIG.toLowerCase().contains("false")) {
			Constants.SEMANTICSEARCH 	= false;
			List<Document> documents 	= Utils.loadDataFromDB(false);
			for (int i = 0; i < documents.size(); i++) {
				System.out.println("SAVE DOCUMENT ID = " + i + " INTO THE MEMCACHED");
				SemantichSearchCached.getInstance().set(Constants.KEY_DOC_NSM + i, documents.get(i));
			}
			SemantichSearchCached.getInstance().set(Constants.KEY_TOTAL_DOCUMENT_NSM, documents.size());
		}
		
	}
	
	public static void loadDictionary() {
		Map<String, String> dictionary = new HashMap<String, String>();
		try {
			File xmlFile = new File(".\\resources\\dictionary\\dictionary.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			Node rootNode = doc.getElementsByTagName("dictionary").item(0);
			NodeList nodeList = rootNode.getChildNodes();
			for (int i = 0; i< nodeList.getLength(); i++) {
				if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					String abbreviation = ((Element) nodeList.item(i)).getAttribute("word");
					String value 		= nodeList.item(i).getTextContent();
					dictionary.put(abbreviation, value);
				}
			}
			SemantichSearchCached.getInstance().set("dictionary", dictionary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, String> getDictionary() {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)SemantichSearchCached.getInstance().get("dictionary");
		return map;
	}

}
