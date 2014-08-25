package com.crawler.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.math.NumberUtils;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class HTMLParserUtil {

	public static Document readXmlStream(InputStream in) throws SAXException,
			IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(in);
		return doc;
	}

	public static ArrayList<Entry> readXml(String fileName)
			throws SAXException, IOException, ParserConfigurationException {
		// Document xml = HTMLParserUtil.readXmlStream(HTMLParserUtil.class
		// .getResourceAsStream("C:/Projects/Local/Look4Re/etc/street.xml"));
		FileInputStream file = new FileInputStream(fileName);
		Document xml = readXmlStream(file);
		NodeList entryNodes = xml.getFirstChild().getChildNodes();
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		for (int i = 0; i < entryNodes.getLength(); i++) {
			Node item = entryNodes.item(i);
			if (item.hasAttributes()) {
				NamedNodeMap attributes = item.getAttributes();
				Entry entry = new Entry(attributes.getNamedItem("key")
						.getTextContent(), attributes.getNamedItem("value")
						.getTextContent());
				entryList.add(entry);
			}
		}
		return entryList;
	}

	public static int stringToInt(String value) {
		return NumberUtils.toInt(value);
	}

	public static String regexpSearch(String rawText, String regExp) {
		String ret = "";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(rawText);
		matcher.find();
		MatchResult result = matcher.toMatchResult();
		try {
			ret = result.group();
		} catch (IllegalStateException e) {// no match found
		}
		return ret;

	}

	public static String regexpSearchGroup(String rawText, String regExp) {
		String ret = "";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(rawText);
		if (matcher.find()) {
			// try {
			ret = matcher.group(1);
			// } catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		return ret;

	}

	public static String extractPlainText(String html) {
		try {
			String ret = null;
			// try {
			Parser parser = Parser.createParser(html, "UTF-8");
			StringBean sb = new StringBean();
			parser.visitAllNodesWith(sb);
			ret = sb.getStrings();
			// } catch (ParserException e) {
			// e.printStackTrace();
			// }
			return ret;
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static String unicodeCompositeToPrecompound(String from) {
		String ret = "";
		if (from != null) {
			ret = from;
			ret = ret.replaceAll("a�?", "á");
			ret = ret.replaceAll("à", "à");
			ret = ret.replaceAll("ạ", "ạ");
			ret = ret.replaceAll("ả", "ả");
			ret = ret.replaceAll("ã", "ã");
			ret = ret.replaceAll("ă", "ă");
			ret = ret.replaceAll("ă�?", "ắ");
			ret = ret.replaceAll("ằ", "ằ");
			ret = ret.replaceAll("ặ", "ặ");
			ret = ret.replaceAll("ẳ", "ẳ");
			ret = ret.replaceAll("ẵ", "ẵ");
			ret = ret.replaceAll("â", "â");
			ret = ret.replaceAll("â�?", "ấ");
			ret = ret.replaceAll("ầ", "ầ");
			ret = ret.replaceAll("ậ", "ậ");
			ret = ret.replaceAll("ẩ", "ẩ");
			ret = ret.replaceAll("ẫ", "ẫ");
			ret = ret.replaceAll("e�?", "é");
			ret = ret.replaceAll("è", "è");
			ret = ret.replaceAll("ẹ", "ẹ");
			ret = ret.replaceAll("ẻ", "ẻ");
			ret = ret.replaceAll("ẽ", "ẽ");
			ret = ret.replaceAll("ê", "ê");
			ret = ret.replaceAll("ê�?", "ế");
			ret = ret.replaceAll("ề", "�?");
			ret = ret.replaceAll("ệ", "ệ");
			ret = ret.replaceAll("ể", "ể");
			ret = ret.replaceAll("ễ", "ễ");
			ret = ret.replaceAll("i�?", "í");
			ret = ret.replaceAll("ì", "ì");
			ret = ret.replaceAll("ị", "ị");
			ret = ret.replaceAll("ỉ", "ỉ");
			ret = ret.replaceAll("ĩ", "ĩ");
			ret = ret.replaceAll("o�?", "ó");
			ret = ret.replaceAll("ò", "ò");
			ret = ret.replaceAll("ọ", "�?");
			ret = ret.replaceAll("ỏ", "�?");
			ret = ret.replaceAll("õ", "õ");
			ret = ret.replaceAll("ơ", "ơ");
			ret = ret.replaceAll("ơ�?", "ớ");
			ret = ret.replaceAll("ờ", "�?");
			ret = ret.replaceAll("ợ", "ợ");
			ret = ret.replaceAll("ở", "ở");
			ret = ret.replaceAll("ỡ", "ỡ");
			ret = ret.replaceAll("ô", "ô");
			ret = ret.replaceAll("ô�?", "ố");
			ret = ret.replaceAll("ồ", "ồ");
			ret = ret.replaceAll("ộ", "ộ");
			ret = ret.replaceAll("ổ", "ổ");
			ret = ret.replaceAll("ỗ", "ỗ");
			ret = ret.replaceAll("u�?", "ú");
			ret = ret.replaceAll("ù", "ù");
			ret = ret.replaceAll("ụ", "ụ");
			ret = ret.replaceAll("ủ", "ủ");
			ret = ret.replaceAll("ũ", "ũ");
			ret = ret.replaceAll("ư", "ư");
			ret = ret.replaceAll("ư�?", "ứ");
			ret = ret.replaceAll("ừ", "ừ");
			ret = ret.replaceAll("ự", "ự");
			ret = ret.replaceAll("ử", "ử");
			ret = ret.replaceAll("ữ", "ữ");
			ret = ret.replaceAll("y�?", "ý");
			ret = ret.replaceAll("ỳ", "ỳ");
			ret = ret.replaceAll("ỵ", "ỵ");
			ret = ret.replaceAll("ỷ", "ỷ");
			ret = ret.replaceAll("ỹ", "ỹ");
			ret = ret.replaceAll("đ", "đ");
		}
		System.out.println("ret=================" + ret);
		return ret;
	}
}