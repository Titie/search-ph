package com.search.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.search.model.Word;

import edu.stanford.nlp.ling.WordTag;


/**
 * 
 * @author AnhH1
 *
 */
public class Utils {
	
	
	/**
	 * This method is used to test some method in this class.
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * This method reads data in file and returns its.
	 * @param filePath
	 * @return
	 */
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
	
	/**
	 * This method will read data in each line and add its value into list.
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileByLine(String filePath) {
		List<String> dataInLines = new ArrayList<String>();
		try {
			File f					= new File(filePath);
			BufferedReader reader 	= new BufferedReader(new FileReader(f));
			String line;
			while((line = reader.readLine()) != null) {
				dataInLines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Has exception when read data in file : " + filePath);
		}
		
		return dataInLines;
	}
	
	/**
	 * This method will read data in each line and add its value into list.
	 * @param filePath
	 * @return
	 */
	public static List<Word> readWordInFile(String filePath) {
		List<Word> dataInLines = new ArrayList<Word>();
		try {
			File f					= new File(filePath);
			BufferedReader reader 	= new BufferedReader(new FileReader(f));
			String line;
			WordTag wordTag = new WordTag();
			while((line = reader.readLine()) != null) {
				wordTag.setWord(line);
				Word word = new Word(null, line, null, null, 1);
				dataInLines.add(word);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Has exception when read data in file : " + filePath);
		}
		
		return dataInLines;
	}
	
	
	/**
	 * This method will return list file in the folder.
	 * @param folder
	 * @return
	 */
	public static List<String> getListFileFromFolder(String folderPath) {
		List<String> listFile = new ArrayList<String>();
		try {
			File folder = new File(folderPath);
			if (folder.isDirectory()) {
				return Arrays.asList(folder.list());
			}
		} catch (Exception e) {
			System.out.println("Has exception when read folder: " + folderPath);
		}
		return listFile;
	}
}
