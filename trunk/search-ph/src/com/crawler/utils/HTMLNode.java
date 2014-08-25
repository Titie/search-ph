package com.crawler.utils;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.NodeList;

/**
 * This class encapsulates data of an HTML DOM node that is extracted by HTMLParser
 * @author TruongDX
 *
 */
public class HTMLNode {
	private Node mNode;

	public HTMLNode(Node node) {
		mNode = node;
	}
	
	/**
	 * Get value of the attribute specified by attrName
	 * @param attrName Name of the attribute 
	 * @return String object represents the value of the attribute
	 */
	public String getAttribute(String attrName) {
		if (TagNode.class.isAssignableFrom(mNode.getClass())) {
			return ((TagNode)mNode).getAttribute(attrName);
		} else {
			return "";
		}
	}
	
	/**
	 * Get the HTML of the node
	 * @return String object represents the HTML of the node
	 */
	public String getHtml() {
		if (mNode == null) {
			return "";
		}
		return mNode.toHtml();
	}
	
	/**
	 * Get the text inside the HTML tag (the node)<br/>
	 * For example: if the HTML of the node is <code>&lt;span&gt;some text&lt;/span&gt;</code>, 
	 * the return string will be <code>some text</code>
	 * @return String object contains the text of the node
	 */
	public String getInnerText() {
		if (mNode == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		NodeList children = mNode.getChildren();
		if (children != null) {
			for (int i = 0; i < children.size(); ++i) {
				Node child = children.elementAt(i);
				if (TextNode.class.isAssignableFrom(child.getClass())) {
					sb.append(child.getText());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Get simple html, especially for mobile usage.
	 * Simple html remove all link, script, comments, etc. from the original html
	 * @return
	 */
	public String getSimpleHtml() {
		return getSimpleHtml(new SimpleHtmlOption());
	}
	public String getSimpleHtml(SimpleHtmlOption option) {
		if (mNode == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		extractSimpleHtml(mNode, option, sb);
		String ret = sb.toString();
		//clean up
		ret = ret.replace("<FONT></FONT>", "");
		ret = ret.replace("<B></B>", "");
		ret = ret.replace("<STRONG></STRONG>", "");
		ret = ret.replace("<EM></EM>", "");
		ret = ret.replace("<DIV></DIV>", "");
		ret = ret.replace("<P></P>", "");
		return ret;
	}

	private void extractSimpleHtml(Node node, SimpleHtmlOption option, StringBuffer out) {
		if (node == null) {
			return;
		}
		if (option == null) option = new SimpleHtmlOption();

		boolean parseChildren = true;
		boolean hasEndTag = true;
		TagNode tagNode = null;
		//OPEN TAG
		if (TextNode.class.isAssignableFrom(node.getClass())) {
			String childText = node.getText();
			if (childText != null && childText.trim().length() > 0 && !"&nbsp;".equals(childText.trim())) {
				out.append(node.getText());						
			}
			parseChildren = false;
			hasEndTag = false;
		} else if (RemarkNode.class.isAssignableFrom(node.getClass())) {
			parseChildren = false;
			hasEndTag = false;
			//do nothing
		} else if (ScriptTag.class.isAssignableFrom(node.getClass())) {
			parseChildren = false;
			hasEndTag = false;
			//do nothing
		} else if (StyleTag.class.isAssignableFrom(node.getClass())) {
			parseChildren = false;
			hasEndTag = false;
			//do nothing
		} else if (TagNode.class.isAssignableFrom(node.getClass())) {
			tagNode = (TagNode) node;
			if (tagNode.getTagName().equalsIgnoreCase("BR")) {
				out.append("<br/>");
				hasEndTag = false;
			} else if (tagNode.getTagName().equalsIgnoreCase("A") 
					|| tagNode.getTagName().equalsIgnoreCase("TABLE") 
					|| tagNode.getTagName().equalsIgnoreCase("TBODY")) {
					//out.append("<br/>");
				hasEndTag = false;
			} else if (tagNode.getTagName().equalsIgnoreCase("TD")) {
				out.append(tagNode.isEndTag() ? option.getTdEndMark() : option.getTdStartMark());
				hasEndTag = false;
			} else if (tagNode.getTagName().equalsIgnoreCase("TR")) {
				out.append(tagNode.isEndTag() ? option.getTrEndMark() : option.getTrStartMark());
				hasEndTag = false;
			} else if (tagNode.getTagName().equalsIgnoreCase("IMG")) {
				if (option.isRetainImage()) {
					String imgSrc = tagNode.getAttribute("src");
					if (StringUtils.isNotEmpty(option.getBaseUrl())) {
						if (StringUtils.isNotEmpty(imgSrc) && !imgSrc.startsWith("http://")) {
							imgSrc = imgSrc.startsWith("/") ? option.getBaseUrl() + imgSrc : option.getBaseUrl() + "/" + imgSrc;
						}
						tagNode.setAttribute("src", imgSrc);
					}
					if (option.isCalculateImageSize() && StringUtils.isNotEmpty(imgSrc)) {
						//calculate width and height of the image (if necessary)
						String strWidth = tagNode.getAttribute("width");
						String strHeight = tagNode.getAttribute("height");
						if (StringUtils.isEmpty(strWidth) || StringUtils.isEmpty(strHeight)) {
							byte[] imgData = HTMLUtil.loadBinary(imgSrc);
							if (imgData != null) {
								int[] imgSize = new int[] {0, 0};
								try {
									imgSize = ImageUtil.getImageSize(imgData);
								} catch (Exception ignore) {}
								tagNode.setAttribute("width", "" + imgSize[0]);
								tagNode.setAttribute("height", "" + imgSize[1]);
							}
						}
					}
	
					out.append(tagNode.toHtml()).append(tagNode.isEndTag() ? option.getImgEndMark() : option.getImgStartMark());
				}
				hasEndTag = false;
			} else if (tagNode.getTagName().equalsIgnoreCase("STRONG") 
					|| tagNode.getTagName().equalsIgnoreCase("B") 
					|| tagNode.getTagName().equalsIgnoreCase("EM") 
					|| tagNode.getTagName().equalsIgnoreCase("I")) {
				out.append(tagNode.toHtml());
				hasEndTag = false;
			} else {
				out.append("<").append(tagNode.getTagName()).append(">");
			}
		} else {
			out.append(node.getText());
			parseChildren = false;
			hasEndTag = false;
		}

		//CHILDREN
		if (parseChildren) {
			NodeList children = node.getChildren();
			if (children != null) {
				for (int i = 0; i < children.size(); ++i) {
					Node child = children.elementAt(i);
					extractSimpleHtml(child, option, out);
				}
			}
		}

		//END TAG
		if (hasEndTag && tagNode != null) {
			out.append("</").append(tagNode.getTagName()).append(">");
		}
	
	}

}
