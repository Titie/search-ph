package com.search.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;


public class Utils {
	
	
	public static void main(String[] args) {

		
		
		VietnameseMaxentTagger vietnameseMaxentTagger = new VietnameseMaxentTagger();
		String file = "C:\\Users\\anhh1\\Desktop\\stopwords.txt";
		String dataInFile = readFile(file);
		
		List<WordTag> wordTags = vietnameseMaxentTagger.tagText2(dataInFile);
		Set<String> tags = new HashSet<String>();
		for (WordTag wordTag : wordTags) {
			tags.add(wordTag.tag());
		}
		System.out.println(tags);
	}
	
	
	public static String readFile(String filePath) {
		try {
			File f = new File(filePath);
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			return "";
		}

	}
}
