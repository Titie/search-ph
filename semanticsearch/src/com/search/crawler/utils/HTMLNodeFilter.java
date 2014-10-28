package com.search.crawler.utils;

import java.util.HashMap;
import java.util.Map;

public class HTMLNodeFilter {
	private String mTagName;
	private Map<String,String> mAttributes = new HashMap<String,String>();
	private int mIndex;
	public String getTagName() {
		return mTagName;
	}
	public void setTagName(String tagName) {
		mTagName = tagName;
	}
	public Map<String, String> getAttributes() {
		return mAttributes;
	}
	public void addAttribute(String attrName, String attrValue) {
		mAttributes.put(attrName, attrValue);
	}
	public int getIndex() {
		return mIndex;
	}
	public void setIndex(int index) {
		mIndex = index;
	}
	
}
