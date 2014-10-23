package com.search.cached;

import java.util.List;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.search.model.Document;
import com.search.tfidf.TFIDF;
import com.search.utils.ConfigurationUtils;
import com.search.utils.Constants;
import com.search.utils.Utils;

/**
 * 
 * @author AnhH1
 *
 */
public class SemantichSearchCached {
	
	private static SemantichSearchCached INSTANCE;
	private MemCachedClient mcc = new MemCachedClient();
	
	private static final String HOST 		= ConfigurationUtils.get(Constants.HOST);
	private static final String WEIGHTS 	= ConfigurationUtils.get(Constants.WEIGHTS);
	private static final String INIT_CONN 	= ConfigurationUtils.get(Constants.INIT_CONN);
	private static final String MIN_CONN 	= ConfigurationUtils.get(Constants.MIN_CONN);
	private static final String MAX_CONN 	= ConfigurationUtils.get(Constants.MAX_CONN);
	private static final String MAINT_SLEEP = ConfigurationUtils.get(Constants.MAINT_SLEEP);
	private static final String NAGLE 		= ConfigurationUtils.get(Constants.NAGLE);
	private static final String MAX_IDLE 	= ConfigurationUtils.get(Constants.MAX_IDLE);
	private static final String SOCKET_TO 	= ConfigurationUtils.get(Constants.SOCKET_TO);
	private static final String SOCKET_CONNECT_TO = ConfigurationUtils.get(Constants.SOCKET_CONNECT_TO);
	

	public static void main(String[] args) {
		List<Document> documents 	= Utils.loadDataFromDB();
		TFIDF tfidfCache 			= new TFIDF(documents);
		tfidfCache.processDocumentsAndCalculateTFIDF();
		SemantichSearchCached.getInstance().set("TFIDF", tfidfCache);
	}

	
	/**
	 * 
	 * @return the SemantichSearchCached
	 */
	static public SemantichSearchCached getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SemantichSearchCached();
		}
		return INSTANCE;
	}

	/**
	 * SemantichSearchCached Constructor
	 */
	private SemantichSearchCached() {// Begin SemantichSearchCached Constructor
		String location = "SemantichSearchCached() - ";
		System.out.println();
		System.out.println( location + "BEGIN" );
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(HOST.split(","));
		pool.setWeights(getWeightsProperty(WEIGHTS)); // 3,3
		pool.setInitConn(Integer.parseInt(INIT_CONN)); // 5
		pool.setMinConn(Integer.parseInt(MIN_CONN)); // 5
		pool.setMaxConn(Integer.parseInt(MAX_CONN)); // 250
		pool.setMaintSleep(Integer.parseInt(MAINT_SLEEP)); // 30
		pool.setNagle(Boolean.parseBoolean(NAGLE)); // FALSE
		pool.setMaxIdle(Long.parseLong(MAX_IDLE)); // 21600000
		pool.setSocketTO(Integer.parseInt(SOCKET_TO)); // 3000
		pool.setSocketConnectTO(Integer.parseInt(SOCKET_CONNECT_TO)); // 0
		pool.initialize();
		System.out.println( location + "END" );
	}// End SemantichSearchCached Constructor


	/**
	 * getWeightsProperty()
	 * @param weights
	 * @return
	 */
	private Integer[] getWeightsProperty(String weights) {//Begin getWeightsProperty
		String location = " getWeightsProperty() -  ";
		System.out.println( location + "BEGIN weights = " + weights );
		String[] strWeights = WEIGHTS.split(",");
		Integer[] IntWeights = new Integer[strWeights.length];
		int i = 0;
		for (String strWeight : strWeights) {
			IntWeights[i] = Integer.valueOf(strWeight);
			i++;
		}
		System.out.println( location + "END" );
		return IntWeights;
	}//End getWeightsProperty
	

	/**
	 * get( String key ) - get object by key
	 * @param key
	 * @return the Object
	 */
	public Object get(String key) {
		String location = "get() - ";
		System.out.println( location + "BEGIN key = " + key );
		Object obj = mcc.get(key);
		if (obj == null) {
			System.out.println(this.getClass().getName() + " get : Object NULL");
		} else {
			System.out.println(this.getClass().getName() + " get : "+ obj.toString());
		}
		return obj;
	}

	/**
	 * set ( String key, Object value ) - 
	 * set object to memcached Server
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		System.out.println(this.getClass().getName() + " set(key) : " + key);
		if (mcc.set(key, value)) {
			System.out.println(this.getClass().getName() + " set key success");
		} else {
			System.out.println(this.getClass().getName() + " set key error");
		}
	}

	/**
	 * delete ( String key )
	 *  - delete bbject by key
	 * 
	 * @param key
	 */
	public void delete(String key) {
		
		System.out.println(this.getClass().getName() + " delete(key) : " + key);
		if (mcc.delete(key)) {
			System.out.println(this.getClass().getName()
					+ " delete key success");
		} else {
			System.out.println(this.getClass().getName() + " delete key error");
		}
	}
}
