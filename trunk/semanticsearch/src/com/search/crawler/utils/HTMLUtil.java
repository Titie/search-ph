package com.search.crawler.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;

public class HTMLUtil {
	/**
	 * 
	 * @param url
	 * @return List - First element is textual content, other elements are images
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List extractData(String html, String baseURL, String relativeFolder) {
		List<Image> images = new ArrayList<Image>();
		Parser parser;
		Lexer lexer;
		Node node;
		TagNode tag;

		String tmp;

		StringBuffer output = new StringBuffer();
		try {
			 parser = Parser.createParser(html, "UTF-8");
			 lexer = parser.getLexer();
			 node = null;
			 boolean isScript = false;
			 while ((node = lexer.nextNode()) != null) {
				 if (TagNode.class.isAssignableFrom(node.getClass())) {
					 tag = (TagNode) node;
					 if (tag instanceof ImageTag) {
						 ImageTag imgTag = (ImageTag) tag;
						 String src = imgTag.getAttribute("src");
						 //output.append(tag.toHtml());
						 byte[] imgContent = loadBinary(baseURL + src);
						 //Image img = new Image(src, ImageUtil.resizeImage(imgContent, 120, 100));
						 Image img = new Image(src, imgContent);
						 img.setFileName(relativeFolder + "/" + System.currentTimeMillis() + ".jpg"); // + img.getOrgFileName());
						 images.add(img);
						 int[] imgSize = new int[] {0, 0};
						 try {
							 imgSize = ImageUtil.getImageSize(imgContent);
						 } catch (Exception ignore) {}
						 //output.append("\r\n<img>").append(img.getFileName()).append("</img>\r\n");
						 output.append("\r\n<img src=\"").append(img.getFileName()).append("\"")
						 .append(" width=\"").append(imgSize[0]).append("\"")
						 .append(" height=\"").append(imgSize[1]).append("\"")
						 .append(">")
						 .append("</img>\r\n");
					 } else if (tag.getTagName().equalsIgnoreCase("p") 
							 || tag.getTagName().equalsIgnoreCase("div")) {
						 output.append("\r\n");
					 } else if (tag.getTagName().equalsIgnoreCase("br")) {
						 output.append("\r\n");
					 } else if (tag.getTagName().equalsIgnoreCase("script")) {
						 if (tag.isEndTag()) {
							 isScript = false;
						 } else {
							 isScript = true;
						 }
					 }
				 } else {
					 if (!isScript) {
					 tmp = node.toHtml().trim();
						 if (tmp.trim().length() > 0) {
							output.append(tmp);
						}
					 }
				 }
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 
		 String content = HTMLUtil.decodeHTMLEntities(output.toString());
		 content = HTMLUtil.UTF8LiteralToChars(content);
		 
		 List ret = new ArrayList();
		 ret.add(content);
		 ret.addAll(images);
		 return ret;
	}	
	
	public static byte[] loadBinary(String imgSrc) {
		BufferedInputStream  bis = null;
		BufferedOutputStream bos = null;

		try {
			URL url = new URL(imgSrc);
			HttpURLConnection conn;
			ByteArrayOutputStream baos;
			conn = (HttpURLConnection) url.openConnection();
			bis = new BufferedInputStream(conn.getInputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			baos = new ByteArrayOutputStream();
			while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				baos.write(buff, 0, bytesRead);
			}
			bis.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	public static String UTF8LiteralToChars(String raw) {
		if (raw == null || raw.trim().length() == 0) {
			return raw;
		}
		 String content = raw;
		 String content1 = raw;
		 String exp = "&#[0-9]*;";
		 Pattern patt = Pattern.compile(exp);
		 Matcher matcher = patt.matcher(content);
		 while (matcher.find()) {
			 String utf8Literal = matcher.group();
			 String utf8CodeStr = utf8Literal.substring(2, utf8Literal.length() - 1);
			 char[] chars = new char[]{(char) Integer.parseInt(utf8CodeStr)};
			 content1 = content1.replaceAll(utf8Literal, new String(chars));
		 }
		 return content1;
	}
	
	public static String decodeHTMLEntities(String html) {
		String decodedHTML = html.replace("&nbsp;"," ");
		decodedHTML = decodedHTML.replace("&amp;", "&");
		decodedHTML = decodedHTML.replace("&copy;", "(c)");
		decodedHTML = decodedHTML.replace("&gt;", ">");
		decodedHTML = decodedHTML.replace("&lt;", "<");
		decodedHTML = decodedHTML.replace("&#160;"," ");
		decodedHTML = decodedHTML.replace("&nbsp;"," ");
		decodedHTML = decodedHTML.replace("&#161;","¡");
		decodedHTML = decodedHTML.replace("&iexcl;","¡");
		decodedHTML = decodedHTML.replace("&#162;","¢");
		decodedHTML = decodedHTML.replace("&cent;","¢");
		decodedHTML = decodedHTML.replace("&#163;","£");
		decodedHTML = decodedHTML.replace("&pound;","£");
		decodedHTML = decodedHTML.replace("&#164;","¤");
		decodedHTML = decodedHTML.replace("&curren;","¤");
		decodedHTML = decodedHTML.replace("&#165;","¥");
		decodedHTML = decodedHTML.replace("&yen;","¥");
		decodedHTML = decodedHTML.replace("&#166;","¦");
		decodedHTML = decodedHTML.replace("&brvbar;","¦");
		decodedHTML = decodedHTML.replace("&#167;","§");
		decodedHTML = decodedHTML.replace("&sect;","§");
		decodedHTML = decodedHTML.replace("&#168;","¨");
		decodedHTML = decodedHTML.replace("&uml;","¨");
		decodedHTML = decodedHTML.replace("&#169;","©");
		decodedHTML = decodedHTML.replace("&copy;","©");
		decodedHTML = decodedHTML.replace("&#170;","ª");
		decodedHTML = decodedHTML.replace("&ordf;","ª");
		decodedHTML = decodedHTML.replace("&#171;","«");
		decodedHTML = decodedHTML.replace("&laquo;","«");
		decodedHTML = decodedHTML.replace("&#172;","¬");
		decodedHTML = decodedHTML.replace("&not;","¬");
		decodedHTML = decodedHTML.replace("&#173;","­");
		decodedHTML = decodedHTML.replace("&shy;","­");
		decodedHTML = decodedHTML.replace("&#174;","®");
		decodedHTML = decodedHTML.replace("&reg;","®");
		decodedHTML = decodedHTML.replace("&#175;","¯");
		decodedHTML = decodedHTML.replace("&macr;","¯");
		decodedHTML = decodedHTML.replace("&#176;","°");
		decodedHTML = decodedHTML.replace("&deg;","°");
		decodedHTML = decodedHTML.replace("&#177;","±");
		decodedHTML = decodedHTML.replace("&plusmn;","±");
		decodedHTML = decodedHTML.replace("&#178;","²");
		decodedHTML = decodedHTML.replace("&sup2;","²");
		decodedHTML = decodedHTML.replace("&#179;","³");
		decodedHTML = decodedHTML.replace("&sup3;","³");
		decodedHTML = decodedHTML.replace("&#180;","´");
		decodedHTML = decodedHTML.replace("&acute;","´");
		decodedHTML = decodedHTML.replace("&#181;","µ");
		decodedHTML = decodedHTML.replace("&micro;","µ");
		decodedHTML = decodedHTML.replace("&#182;","¶");
		decodedHTML = decodedHTML.replace("&para;","¶");
		decodedHTML = decodedHTML.replace("&#183;","·");
		decodedHTML = decodedHTML.replace("&middot;","·");
		decodedHTML = decodedHTML.replace("&#184;","¸");
		decodedHTML = decodedHTML.replace("&cedil;","¸");
		decodedHTML = decodedHTML.replace("&#185;","¹");
		decodedHTML = decodedHTML.replace("&sup1;","¹");
		decodedHTML = decodedHTML.replace("&#186;","º");
		decodedHTML = decodedHTML.replace("&ordm;","º");
		decodedHTML = decodedHTML.replace("&#187;","»");
		decodedHTML = decodedHTML.replace("&raquo;","»");
		decodedHTML = decodedHTML.replace("&#188;","¼");
		decodedHTML = decodedHTML.replace("&frac14;","¼");
		decodedHTML = decodedHTML.replace("&#189;","½");
		decodedHTML = decodedHTML.replace("&frac12;","½");
		decodedHTML = decodedHTML.replace("&#190;","¾");
		decodedHTML = decodedHTML.replace("&frac34;","¾");
		decodedHTML = decodedHTML.replace("&#191;","¿");
		decodedHTML = decodedHTML.replace("&iquest;","¿");
		decodedHTML = decodedHTML.replace("&#215;","×");
		decodedHTML = decodedHTML.replace("&times;","×");
		decodedHTML = decodedHTML.replace("&#247;","÷");
		decodedHTML = decodedHTML.replace("&divide;","÷");
		decodedHTML = decodedHTML.replace("&#192;","À");
		decodedHTML = decodedHTML.replace("&Agrave;","À");
		decodedHTML = decodedHTML.replace("&#193;","�?");
		decodedHTML = decodedHTML.replace("&Aacute;","�?");
		decodedHTML = decodedHTML.replace("&#194;","Â");
		decodedHTML = decodedHTML.replace("&Acirc;","Â");
		decodedHTML = decodedHTML.replace("&#195;","Ã");
		decodedHTML = decodedHTML.replace("&Atilde;","Ã");
		decodedHTML = decodedHTML.replace("&#196;","Ä");
		decodedHTML = decodedHTML.replace("&Auml;","Ä");
		decodedHTML = decodedHTML.replace("&#197;","Å");
		decodedHTML = decodedHTML.replace("&Aring;","Å");
		decodedHTML = decodedHTML.replace("&#198;","Æ");
		decodedHTML = decodedHTML.replace("&AElig;","Æ");
		decodedHTML = decodedHTML.replace("&#199;","Ç");
		decodedHTML = decodedHTML.replace("&Ccedil;","Ç");
		decodedHTML = decodedHTML.replace("&#200;","È");
		decodedHTML = decodedHTML.replace("&Egrave;","È");
		decodedHTML = decodedHTML.replace("&#201;","É");
		decodedHTML = decodedHTML.replace("&Eacute;","É");
		decodedHTML = decodedHTML.replace("&#202;","Ê");
		decodedHTML = decodedHTML.replace("&Ecirc;","Ê");
		decodedHTML = decodedHTML.replace("&#203;","Ë");
		decodedHTML = decodedHTML.replace("&Euml;","Ë");
		decodedHTML = decodedHTML.replace("&#204;","Ì");
		decodedHTML = decodedHTML.replace("&Igrave;","Ì");
		decodedHTML = decodedHTML.replace("&#205;","�?");
		decodedHTML = decodedHTML.replace("&Iacute;","�?");
		decodedHTML = decodedHTML.replace("&#206;","Î");
		decodedHTML = decodedHTML.replace("&Icirc;","Î");
		decodedHTML = decodedHTML.replace("&#207;","�?");
		decodedHTML = decodedHTML.replace("&Iuml;","�?");
		decodedHTML = decodedHTML.replace("&#208;","�?");
		decodedHTML = decodedHTML.replace("&ETH;","�?");
		decodedHTML = decodedHTML.replace("&#209;","Ñ");
		decodedHTML = decodedHTML.replace("&Ntilde;","Ñ");
		decodedHTML = decodedHTML.replace("&#210;","Ò");
		decodedHTML = decodedHTML.replace("&Ograve;","Ò");
		decodedHTML = decodedHTML.replace("&#211;","Ó");
		decodedHTML = decodedHTML.replace("&Oacute;","Ó");
		decodedHTML = decodedHTML.replace("&#212;","Ô");
		decodedHTML = decodedHTML.replace("&Ocirc;","Ô");
		decodedHTML = decodedHTML.replace("&#213;","Õ");
		decodedHTML = decodedHTML.replace("&Otilde;","Õ");
		decodedHTML = decodedHTML.replace("&#214;","Ö");
		decodedHTML = decodedHTML.replace("&Ouml;","Ö");
		decodedHTML = decodedHTML.replace("&#216;","Ø");
		decodedHTML = decodedHTML.replace("&Oslash;","Ø");
		decodedHTML = decodedHTML.replace("&#217;","Ù");
		decodedHTML = decodedHTML.replace("&Ugrave;","Ù");
		decodedHTML = decodedHTML.replace("&#218;","Ú");
		decodedHTML = decodedHTML.replace("&Uacute;","Ú");
		decodedHTML = decodedHTML.replace("&#219;","Û");
		decodedHTML = decodedHTML.replace("&Ucirc;","Û");
		decodedHTML = decodedHTML.replace("&#220;","Ü");
		decodedHTML = decodedHTML.replace("&Uuml;","Ü");
		decodedHTML = decodedHTML.replace("&#221;","�?");
		decodedHTML = decodedHTML.replace("&Yacute;","�?");
		decodedHTML = decodedHTML.replace("&#222;","Þ");
		decodedHTML = decodedHTML.replace("&THORN;","Þ");
		decodedHTML = decodedHTML.replace("&#223;","ß");
		decodedHTML = decodedHTML.replace("&szlig;","ß");
		decodedHTML = decodedHTML.replace("&#224;","à");
		decodedHTML = decodedHTML.replace("&agrave;","à");
		decodedHTML = decodedHTML.replace("&#225;","á");
		decodedHTML = decodedHTML.replace("&aacute;","á");
		decodedHTML = decodedHTML.replace("&#226;","â");
		decodedHTML = decodedHTML.replace("&acirc;","â");
		decodedHTML = decodedHTML.replace("&#227;","ã");
		decodedHTML = decodedHTML.replace("&atilde;","ã");
		decodedHTML = decodedHTML.replace("&#228;","ä");
		decodedHTML = decodedHTML.replace("&auml;","ä");
		decodedHTML = decodedHTML.replace("&#229;","å");
		decodedHTML = decodedHTML.replace("&aring;","å");
		decodedHTML = decodedHTML.replace("&#230;","æ");
		decodedHTML = decodedHTML.replace("&aelig;","æ");
		decodedHTML = decodedHTML.replace("&#231;","ç");
		decodedHTML = decodedHTML.replace("&ccedil;","ç");
		decodedHTML = decodedHTML.replace("&#232;","è");
		decodedHTML = decodedHTML.replace("&egrave;","è");
		decodedHTML = decodedHTML.replace("&#233;","é");
		decodedHTML = decodedHTML.replace("&eacute;","é");
		decodedHTML = decodedHTML.replace("&#234;","ê");
		decodedHTML = decodedHTML.replace("&ecirc;","ê");
		decodedHTML = decodedHTML.replace("&#235;","ë");
		decodedHTML = decodedHTML.replace("&euml;","ë");
		decodedHTML = decodedHTML.replace("&#236;","ì");
		decodedHTML = decodedHTML.replace("&igrave;","ì");
		decodedHTML = decodedHTML.replace("&#237;","í");
		decodedHTML = decodedHTML.replace("&iacute;","í");
		decodedHTML = decodedHTML.replace("&#238;","î");
		decodedHTML = decodedHTML.replace("&icirc;","î");
		decodedHTML = decodedHTML.replace("&#239;","ï");
		decodedHTML = decodedHTML.replace("&iuml;","ï");
		decodedHTML = decodedHTML.replace("&#240;","ð");
		decodedHTML = decodedHTML.replace("&eth;","ð");
		decodedHTML = decodedHTML.replace("&#241;","ñ");
		decodedHTML = decodedHTML.replace("&ntilde;","ñ");
		decodedHTML = decodedHTML.replace("&#242;","ò");
		decodedHTML = decodedHTML.replace("&ograve;","ò");
		decodedHTML = decodedHTML.replace("&#243;","ó");
		decodedHTML = decodedHTML.replace("&oacute;","ó");
		decodedHTML = decodedHTML.replace("&#244;","ô");
		decodedHTML = decodedHTML.replace("&ocirc;","ô");
		decodedHTML = decodedHTML.replace("&#245;","õ");
		decodedHTML = decodedHTML.replace("&otilde;","õ");
		decodedHTML = decodedHTML.replace("&#246;","ö");
		decodedHTML = decodedHTML.replace("&ouml;","ö");
		decodedHTML = decodedHTML.replace("&#248;","ø");
		decodedHTML = decodedHTML.replace("&oslash;","ø");
		decodedHTML = decodedHTML.replace("&#249;","ù");
		decodedHTML = decodedHTML.replace("&ugrave;","ù");
		decodedHTML = decodedHTML.replace("&#250;","ú");
		decodedHTML = decodedHTML.replace("&uacute;","ú");
		decodedHTML = decodedHTML.replace("&#251;","û");
		decodedHTML = decodedHTML.replace("&ucirc;","û");
		decodedHTML = decodedHTML.replace("&#252;","ü");
		decodedHTML = decodedHTML.replace("&uuml;","ü");
		decodedHTML = decodedHTML.replace("&#253;","ý");
		decodedHTML = decodedHTML.replace("&yacute;","ý");
		decodedHTML = decodedHTML.replace("&#254;","þ");
		decodedHTML = decodedHTML.replace("&thorn;","þ");
		decodedHTML = decodedHTML.replace("&#255;","ÿ");
		decodedHTML = decodedHTML.replace("&yuml;","ÿ");
		return decodedHTML;
	}
	
}
