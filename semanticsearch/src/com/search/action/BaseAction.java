package com.search.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author AnhH1
 * 
 */
public class BaseAction extends ActionSupport implements ServletContextAware {

	private static final long serialVersionUID = -1050022320449630709L;

	private String action;
	protected ServletContext servletContext;

	public static final String NOTFOUND = "notfound";

	/**
	 * Check is given action or not.
	 * 
	 * @param action
	 *            action name
	 * @return <code>true</code> or <code>false</code>
	 */
	public boolean isAction(String action) {
		return action.equalsIgnoreCase(this.action);
	}

	/**
	 * Convenience method to get the request.
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response.
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Get context path. e.g http://localhost:8080/ezShop/
	 * 
	 * @return context path.
	 */
	public String getContextPath() {
		return getRequest().getContextPath();
	}

	/**
	 * Set servlet context when the action is loaded.
	 * 
	 * @param servletContext
	 *            java servlet context
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
