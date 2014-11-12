package com.search.crawler.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NotFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.NodeTreeWalker;
import org.htmlparser.util.ParserException;



public class HTMLParser {
	private String mHtml;
	private NodeList mNodeList;
	private Parser mParser;
	private boolean mDepthFirst = true;
	URLLoader urlLoader = new URLLoader();
	NameValuePair[] parameters = null;
	private String cache = "";
	
	public void loadURL(String url) {
		try {
			//URLLoader urlLoader = new URLLoader();
			mHtml = urlLoader.load(url);
			mParser = Parser.createParser(mHtml, "UTF-8");
			//parse();
		} finally {
			//System.gc();;
		}
	}


	public void loadURL(String url, boolean depthFirst) {
		mDepthFirst = depthFirst;
		loadURL(url);
	}
	
	public URLLoader getUrlLoader() {
		return urlLoader;
	}
	
	public String getHtml() {
		return mHtml;
	}
	public void setHtml(String html) {
		try {
			mHtml = html;
			mParser = Parser.createParser(mHtml, "UTF-8");
			//parse();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//System.gc();;
		}
	}

	public void setHtml(String html, boolean depthFirst) {
		mDepthFirst = depthFirst;
		setHtml(html);
	}

	public HTMLNode getNodeAt(int index) {
		Node node = mNodeList.elementAt(index);
		HTMLNode htmlNode = new HTMLNode(node);
		return htmlNode;
	}
	
	public int getNumberOfNodes() {
		if (mNodeList == null) {
			return 0;
		} else {
			return mNodeList.size();
		}
	}
	public void filter(HTMLNodeFilter htmlNodeFilter) {
		try {
			if (htmlNodeFilter != null) {
				List<NodeFilter> filters = new ArrayList<NodeFilter>();
				if (htmlNodeFilter.getTagName() != null && htmlNodeFilter.getTagName().trim().length() > 0) {
					TagNameFilter tagNameFilter = new TagNameFilter(htmlNodeFilter.getTagName());
					filters.add(tagNameFilter);
				}
				if (htmlNodeFilter.getAttributes() != null) {
					Set<String> attrNames = htmlNodeFilter.getAttributes().keySet();
					for (String attrName : attrNames) {
						HasAttributeFilter attrFilter = new HasAttributeFilter(attrName, htmlNodeFilter.getAttributes().get(attrName));
						filters.add(attrFilter);
					}
				}
				AndFilter andFilter = new AndFilter((NodeFilter[]) filters.toArray(new NodeFilter[]{}));
	
				mNodeList = mParser.extractAllNodesThatMatch(andFilter);
			} else {
				mNodeList = mParser.extractAllNodesThatMatch(null);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		} finally {
			//System.gc();;
		}
	}	
	
	public void filter(String tagName, String attributeFilter) {
		if (StringUtils.isEmpty(tagName) && StringUtils.isEmpty(attributeFilter)) {
			filter((HTMLNodeFilter) null);
			return;
		} 
		List<NodeFilter> filters = new ArrayList<NodeFilter>();
		if (!StringUtils.isEmpty(tagName)) {
			TagNameFilter tagNameFilter = new TagNameFilter(tagName);
			filters.add(tagNameFilter);
		}
		if (!StringUtils.isEmpty(attributeFilter)) {
			StringTokenizer attStk = new StringTokenizer(attributeFilter.trim(), ";");
			while (attStk.hasMoreTokens()) {
				StringTokenizer stk = new StringTokenizer(attStk.nextToken(), ":");
				if (stk.countTokens() == 2) {
					String attrName = stk.nextToken();
					String attrVal = stk.nextToken();
					HasAttributeFilter attrFilter = new HasAttributeFilter(attrName.trim(), attrVal.trim());
					filters.add(attrFilter);
				}
			}
		}
		AndFilter andFilter = new AndFilter((NodeFilter[]) filters.toArray(new NodeFilter[]{}));
		try {
			mNodeList = mParser.extractAllNodesThatMatch(andFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		} finally {
			//System.gc();;
		}
		
	}
	
	public void exclude(HTMLNodeFilter htmlNodeFilter) {
		try {
			if (htmlNodeFilter != null) {
				List<NodeFilter> filters = new ArrayList<NodeFilter>();
				if (htmlNodeFilter.getTagName() != null && htmlNodeFilter.getTagName().trim().length() > 0) {
					TagNameFilter tagNameFilter = new TagNameFilter(htmlNodeFilter.getTagName());
					filters.add(tagNameFilter);
				}
				if (htmlNodeFilter.getAttributes() != null) {
					Set<String> attrNames = htmlNodeFilter.getAttributes().keySet();
					for (String attrName : attrNames) {
						HasAttributeFilter attrFilter = new HasAttributeFilter(attrName, htmlNodeFilter.getAttributes().get(attrName));
						filters.add(attrFilter);
					}
				}
				AndFilter andFilter = new AndFilter((NodeFilter[]) filters.toArray(new NodeFilter[]{}));
	
				mNodeList = mParser.extractAllNodesThatMatch(new NotFilter(andFilter));
			} else {
				mNodeList = mParser.extractAllNodesThatMatch(null);
			}
		} catch (ParserException e) {
			e.printStackTrace();
		} finally {
			//System.gc();;
		}
	}		
	
	public void exclude(String tagName, String attributeFilter) {
		if (StringUtils.isEmpty(tagName) && StringUtils.isEmpty(attributeFilter)) {
			filter((HTMLNodeFilter) null);
			return;
		} 
		List<NodeFilter> filters = new ArrayList<NodeFilter>();
		if (!StringUtils.isEmpty(tagName)) {
			TagNameFilter tagNameFilter = new TagNameFilter(tagName);
			filters.add(tagNameFilter);
		}
		if (!StringUtils.isEmpty(attributeFilter)) {
			StringTokenizer attStk = new StringTokenizer(attributeFilter.trim(), ";");
			while (attStk.hasMoreTokens()) {
				StringTokenizer stk = new StringTokenizer(attStk.nextToken(), ":");
				if (stk.countTokens() == 2) {
					String attrName = stk.nextToken();
					String attrVal = stk.nextToken();
					HasAttributeFilter attrFilter = new HasAttributeFilter(attrName.trim(), attrVal.trim());
					filters.add(attrFilter);
				}
			}
		}
		AndFilter andFilter = new AndFilter((NodeFilter[]) filters.toArray(new NodeFilter[]{}));
		try {
			//filter(new HTMLNodeFilter()); //repopulate mNodeList
			NodeList nodeList = mNodeList.extractAllNodesThatMatch(andFilter);
			for (int i = 0; i < nodeList.size(); ++i) {
				Node node = nodeList.elementAt(i);
				if (mNodeList.contains(node)) {
					if (node.getParent() != null) {
						node.getParent().getChildren().remove(node);
					}
					mNodeList.remove(node);
				}
			}
//		} catch (ParserException e) {
//			e.printStackTrace();
		} finally {
			//System.gc();;
		}
		
	}	
	public void removeFilter() {
		try {
			mNodeList = mParser.extractAllNodesThatMatch(null);
		} catch (ParserException e) {
			e.printStackTrace();
		} finally {
			//System.gc();;
		}
	}
	
	public int findNode(String tagName, String containedText, boolean matchWholeString) throws Exception {
		int ret = -1;
		for (int i = 0; i < mNodeList.size(); ++i) {
			Node node = mNodeList.elementAt(i);
			if (TagNode.class.isAssignableFrom(node.getClass())) {
				TagNode tag = (TagNode) node;
				if (tag.getTagName().equalsIgnoreCase(tagName)) {
					if (containedText == null) {
						ret = i;
						break;
					} else {
						String plaintext = HTMLParserUtil.extractPlainText(tag.toHtml());
						if (plaintext!= null) {
							plaintext=plaintext.trim();
							if (matchWholeString) {
								if (plaintext.equalsIgnoreCase(containedText)) {
									ret = i;
									break;
								}
							} else {
								if (plaintext.contains(containedText)) {
									ret = i;
									break;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	
	public int findNodeFrom(int fromIndex, String tagName, String containedText, boolean matchWholeString) throws Exception{
		int ret = -1;
		if (fromIndex >= mNodeList.size()) {
			return -1;
		}
		for (int i = fromIndex; i < mNodeList.size(); ++i) {
			Node node = mNodeList.elementAt(i);
			if (TagNode.class.isAssignableFrom(node.getClass())) {
				TagNode tag = (TagNode) node;
				if (tag.getTagName().equalsIgnoreCase(tagName)) {
					if (containedText == null) {
						ret = i;
						break;
					} else {
						String plaintext = HTMLParserUtil.extractPlainText(tag.toHtml()).trim();
						if (matchWholeString) {
							if (plaintext.equalsIgnoreCase(containedText)) {
								ret = i;
								break;
							}
						} else {
							if (plaintext.contains(containedText)) {
								ret = i;
								break;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	@SuppressWarnings("unused")
	private void parse() throws Exception{
		try {
			NodeList nodeList = mParser.parse(null);
			if (nodeList == null) return;
			int i = 0;
			Node rootNode = nodeList.elementAt(i);
			while (!TagNode.class.isAssignableFrom(rootNode.getClass())) {
				rootNode = nodeList.elementAt(++i);
			}
			NodeTreeWalker walker = new NodeTreeWalker(rootNode, mDepthFirst);
			mNodeList  = walker.getRootNode().getChildren();
			//mNodeList = mParser.parse(null);
		//} catch (ParserException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		} finally {
			//System.gc();;
		}
	}
	
	public void setParameters( NameValuePair[] fieldList ){
		try {
			parameters = fieldList;
		} finally {
			//System.gc();;
		}
	}

	
	public void setCache(String cache) {
		try {
			this.cache = cache;
		} finally {
			//System.gc();;
		}
	}

	public String getCache() {
		return cache;
	}
}
