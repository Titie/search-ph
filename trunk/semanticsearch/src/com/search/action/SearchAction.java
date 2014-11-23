package com.search.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.search.cached.CachedService;
import com.search.model.Document;
import com.search.tfidf.SearchDocument;
import com.search.tfidf.TFIDF;
import com.search.utils.Utils;

/**
 * 
 * @author AnhH1
 *
 */
public class SearchAction extends BaseAction {
	
	private static final long serialVersionUID = 6775871449101932201L;
	private String isSubmit;
	private String q;
	private List<Document> retrievals;
	private int 		totalResult;
	private double 		timeTaken;
	private int 		documentId;
	private Document 	document;
	
	
	private int 	pageNo;
	private int 	pageIndex;
	private Integer page;
	private boolean isNext;
	private boolean isBack;
	private Integer pageTemp;
	private Integer pageFirstLoop;
	private Integer pageEndLoop;
	
	public static final int DEFAULT_PAGE_SHOW = 10;
	public static final int DEFAULT_NUMBER_PAGE_SHOW = 10;
	
	public String semanticSearch() {
		String location = "search() - ";
		SearchDocument 	searchDocument 	= new SearchDocument();
		retrievals 						= new ArrayList<Document>();
		
		TFIDF  	tfidf					= (TFIDF)getSession().getAttribute("TFIDF_SM");
		
		if (tfidf == null) {
			System.out.println("TFIDF ---- ADD IN SESSION");
			tfidf = new TFIDF(CachedService.getDocumentListFromMemCached(true));
			tfidf.processDocumentsAndCalculateTFIDF();
			getSession().setAttribute("TFIDF_SM", tfidf);
		}
		
		if ("true".equalsIgnoreCase(isSubmit) && !StringUtils.isBlank(q)) {
			long timeStart = System.currentTimeMillis();
			System.out.println("KEYWORDS: " + q);
			retrievals = searchDocument.searchDocument(q, tfidf, true);
			totalResult = retrievals.size();
			timeTaken = (System.currentTimeMillis() - timeStart)/1000;
			System.out.println(location + "totalResult: " + totalResult);
		}
		
		//Start pagination
		pageNo = getPageNoDocument();
		page = ((pageNo%DEFAULT_PAGE_SHOW != 0) ? (pageNo/DEFAULT_PAGE_SHOW):(pageNo/DEFAULT_PAGE_SHOW));
		
		if ( pageIndex > pageNo) pageIndex = pageNo;
		
		if ( pageIndex < 1) pageIndex = 1;
		
		pageFirstLoop = 1;
		if (pageNo <= DEFAULT_NUMBER_PAGE_SHOW) {
			pageEndLoop = pageNo;
		}

		if (pageIndex < DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW) {
			pageFirstLoop = 1;
			pageEndLoop = DEFAULT_NUMBER_PAGE_SHOW;
		}
		
		if (pageIndex >= DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW && pageIndex < pageNo) {
			pageFirstLoop = pageIndex + 1 - DEFAULT_NUMBER_PAGE_SHOW/2;
			pageEndLoop = pageIndex - 1 + DEFAULT_NUMBER_PAGE_SHOW/2;
		}
		
		if (pageIndex >= DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW && pageIndex + 4 > pageNo) {
			pageFirstLoop = pageNo + 1 - DEFAULT_NUMBER_PAGE_SHOW;
			pageEndLoop = pageNo;
		}
		int startIndex = DEFAULT_PAGE_SHOW * (pageIndex - 1);
		int endIndex = startIndex + DEFAULT_PAGE_SHOW;
		endIndex = ( endIndex > retrievals.size() ? retrievals.size() : endIndex );
		retrievals = retrievals.subList(startIndex, endIndex);

		//END pagination
		
		return SUCCESS;
	}

	
	public String normalSearch() {
		String location = "search() - ";
		SearchDocument 	searchDocument 	= new SearchDocument();
		retrievals 						= new ArrayList<Document>();
		
		TFIDF  	tfidf					= (TFIDF)getSession().getAttribute("TFIDF_NSM");
		
		if (tfidf == null) {
			System.out.println("TFIDF ---- ADD IN SESSION");
			tfidf = new TFIDF(CachedService.getDocumentListFromMemCached(false));
			tfidf.processDocumentsAndCalculateTFIDF();
			getSession().setAttribute("TFIDF_NSM", tfidf);
		}
		
		if ("true".equalsIgnoreCase(isSubmit) && !StringUtils.isBlank(q)) {
			long timeStart = System.currentTimeMillis();
			System.out.println("KEYWORDS: " + q);
			retrievals = searchDocument.searchDocument(q, tfidf, false);
			totalResult = retrievals.size();
			timeTaken = (System.currentTimeMillis() - timeStart)/1000;
			System.out.println(location + "totalResult: " + totalResult);
		}
		
		//Start pagination
		pageNo = getPageNoDocument();
		page = ((pageNo%DEFAULT_PAGE_SHOW != 0) ? (pageNo/DEFAULT_PAGE_SHOW):(pageNo/DEFAULT_PAGE_SHOW));
		
		if ( pageIndex > pageNo) pageIndex = pageNo;
		
		if ( pageIndex < 1) pageIndex = 1;
		
		pageFirstLoop = 1;
		if (pageNo <= DEFAULT_NUMBER_PAGE_SHOW) {
			pageEndLoop = pageNo;
		}

		if (pageIndex < DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW) {
			pageFirstLoop = 1;
			pageEndLoop = DEFAULT_NUMBER_PAGE_SHOW;
		}
		
		if (pageIndex >= DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW && pageIndex < pageNo) {
			pageFirstLoop = pageIndex + 1 - DEFAULT_NUMBER_PAGE_SHOW/2;
			pageEndLoop = pageIndex - 1 + DEFAULT_NUMBER_PAGE_SHOW/2;
		}
		
		if (pageIndex >= DEFAULT_NUMBER_PAGE_SHOW && pageNo > DEFAULT_NUMBER_PAGE_SHOW && pageIndex + 4 > pageNo) {
			pageFirstLoop = pageNo + 1 - DEFAULT_NUMBER_PAGE_SHOW;
			pageEndLoop = pageNo;
		}
		int startIndex = DEFAULT_PAGE_SHOW * (pageIndex - 1);
		int endIndex = startIndex + DEFAULT_PAGE_SHOW;
		endIndex = ( endIndex > retrievals.size() ? retrievals.size() : endIndex );
		retrievals = retrievals.subList(startIndex, endIndex);

		//END pagination
		
		return SUCCESS;
	}
	
	
	public String details() {
		document = Utils.getDocumentByDocumentId(documentId);
		if (document != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	
	public int getPageNoDocument() {
		int size = 0;
		size = retrievals.size();
		pageNo = size/DEFAULT_PAGE_SHOW;
		
		if (size%DEFAULT_PAGE_SHOW == 0)
			return pageNo;
		
		return (pageNo + 1);
	}
	
	
	/**
	 * @return the isSubmit
	 */
	public String getIsSubmit() {
		return isSubmit;
	}


	/**
	 * @param isSubmit the isSubmit to set
	 */
	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}


	/**
	 * @return the totalResult
	 */
	public int getTotalResult() {
		return totalResult;
	}


	/**
	 * @param totalResult the totalResult to set
	 */
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}




	/**
	 * @return the timeTaken
	 */
	public double getTimeTaken() {
		return timeTaken;
	}


	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}


	/**
	 * @return the retrievals
	 */
	public List<Document> getRetrievals() {
		return retrievals;
	}


	/**
	 * @param retrievals the retrievals to set
	 */
	public void setRetrievals(List<Document> retrievals) {
		this.retrievals = retrievals;
	}


	/**
	 * @return the q
	 */
	public String getQ() {
		return q;
	}


	/**
	 * @param q the q to set
	 */
	public void setQ(String q) {
		this.q = q;
	}


	/**
	 * @return the documentId
	 */
	public int getDocumentId() {
		return documentId;
	}


	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}


	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}


	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}


	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}


	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}


	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}


	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}


	/**
	 * @return the isNext
	 */
	public boolean isNext() {
		return isNext;
	}


	/**
	 * @param isNext the isNext to set
	 */
	public void setNext(boolean isNext) {
		this.isNext = isNext;
	}


	/**
	 * @return the isBack
	 */
	public boolean isBack() {
		return isBack;
	}


	/**
	 * @param isBack the isBack to set
	 */
	public void setBack(boolean isBack) {
		this.isBack = isBack;
	}


	/**
	 * @return the pageTemp
	 */
	public Integer getPageTemp() {
		return pageTemp;
	}


	/**
	 * @param pageTemp the pageTemp to set
	 */
	public void setPageTemp(Integer pageTemp) {
		this.pageTemp = pageTemp;
	}


	/**
	 * @return the pageFirstLoop
	 */
	public Integer getPageFirstLoop() {
		return pageFirstLoop;
	}


	/**
	 * @param pageFirstLoop the pageFirstLoop to set
	 */
	public void setPageFirstLoop(Integer pageFirstLoop) {
		this.pageFirstLoop = pageFirstLoop;
	}


	/**
	 * @return the pageEndLoop
	 */
	public Integer getPageEndLoop() {
		return pageEndLoop;
	}


	/**
	 * @param pageEndLoop the pageEndLoop to set
	 */
	public void setPageEndLoop(Integer pageEndLoop) {
		this.pageEndLoop = pageEndLoop;
	}

}
