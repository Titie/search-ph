package com.crawler.utils;

public class Entry {
	private String mKey;
	private String mValue;

	public Entry(String key, String value) {
		mKey = key;
		mValue = value;
	}

	public void setKey(String key) {
		mKey = key;
	}

	public void setValue(String value) {
		mValue = value;
	}

	public String getKey() {
		return mKey;
	}

	public String getValue() {
		return mValue;
	}

}
