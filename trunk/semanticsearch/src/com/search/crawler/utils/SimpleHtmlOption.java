package com.search.crawler.utils;

public class SimpleHtmlOption {
	private String baseUrl = "";
	private boolean retainImage = true;
	private boolean calculateImageSize = true;
	private String imgStartMark = "<br/>";
	private String imgEndMark = "<br/>";
	private String trStartMark = "<br/>";
	private String trEndMark = "";
	private String tdStartMark = "";
	private String tdEndMark = "";
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public boolean isRetainImage() {
		return retainImage;
	}
	public void setRetainImage(boolean retainImage) {
		this.retainImage = retainImage;
	}
	public boolean isCalculateImageSize() {
		return calculateImageSize;
	}
	public void setCalculateImageSize(boolean calculateImageSize) {
		this.calculateImageSize = calculateImageSize;
	}
	
	public String getImgStartMark() {
		return imgStartMark;
	}
	public void setImgStartMark(String imgStartMark) {
		this.imgStartMark = imgStartMark;
	}
	public String getImgEndMark() {
		return imgEndMark;
	}
	public void setImgEndMark(String imgEndMark) {
		this.imgEndMark = imgEndMark;
	}
	public String getTrStartMark() {
		return trStartMark;
	}
	public void setTrStartMark(String trStartMark) {
		this.trStartMark = trStartMark;
	}
	public String getTrEndMark() {
		return trEndMark;
	}
	public void setTrEndMark(String trEndMark) {
		this.trEndMark = trEndMark;
	}
	public String getTdStartMark() {
		return tdStartMark;
	}
	public void setTdStartMark(String tdStartMark) {
		this.tdStartMark = tdStartMark;
	}
	public String getTdEndMark() {
		return tdEndMark;
	}
	public void setTdEndMark(String tdEndMark) {
		this.tdEndMark = tdEndMark;
	}
	
	
}
