package com.crawler.utils;

public class Image {
	private String src;
	private String fileName;
	private byte[] content;
	
	public Image(String src, byte[] content) {
		this.src = src;
		this.content = content;
	}
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getOrgFileName() {
		if (src == null) {
			return "";
		}
		int slashPos = src.lastIndexOf("/");
		if (slashPos > -1) {
			return src.substring(slashPos + 1, src.length());
		} else {
			return src;
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public static void main(String arg[]) {
		Image img = new Image("//ad.jpg",null);
		System.out.println(img.getOrgFileName());
	}
	
}
