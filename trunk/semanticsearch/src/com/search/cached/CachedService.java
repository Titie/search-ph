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
	
	public static List<Document> getDocumentListFromMemCached() {
		System.out.println("===========GET DOCUMENT LIST FROM MEMCACHED=======");
		List<Document> documents = new ArrayList<Document>();
		int total = (int)SemantichSearchCached.getInstance().get("TOTALDOCUMENT");
		for (int i = 0; i < total; i++) {
			Document d = (Document)SemantichSearchCached.getInstance().get("doc:" + i);
			documents.add(d);
		}
		return documents;
	}
	
	public static void saveDocumentIntoMemcached() {
		List<Document> documents 	= Utils.loadDataFromDB();
		for (int i = 0; i < documents.size(); i++) {
			System.out.println("SAVE DOCUMENT ID = " + i + " INTO THE MEMCACHED");
			SemantichSearchCached.getInstance().set("doc:" + i, documents.get(i));
		}
		SemantichSearchCached.getInstance().set("TOTALDOCUMENT", documents.size());
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
