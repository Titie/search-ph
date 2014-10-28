package com.search.crawler.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
public class URLLoader {
	
	
	
	public String load(String address) {
		return load(address, null, false);
	}
	
	
	public String load(String address, boolean userSystemProxy) {
		return load(address, null, userSystemProxy);
	}
	
	
	
	public String load(String address, String cookie, boolean userSystemProxy) {
		
//		if (true) {
//			System.setProperty("http.proxyHost", "fsoft-proxy");
//			System.setProperty("http.proxyPort", "8080");		
//			System.setProperty("http.proxyUser", "anhh1");
//			System.setProperty("http.proxyPassword", "NgocBao1@");
//		}
		
		BufferedInputStream  bis = null;
		BufferedOutputStream bos = null;

		try {
			URL url = new URL(address);
			HttpURLConnection conn;
			ByteArrayOutputStream baos;
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10*1000);
			conn.setReadTimeout(10*1000);
			//conn.setInstanceFollowRedirects(false);
			if (cookie != null) {
				conn.setRequestProperty("Cookie", cookie);
			}
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB6)");

			conn.setDoInput(true);
			conn.setDoOutput(true);
			if ("gzip".equalsIgnoreCase(conn.getHeaderField("Content-Encoding"))) {
				bis = new BufferedInputStream(new GZIPInputStream(conn.getInputStream()));
			} else {
				bis = new BufferedInputStream(conn.getInputStream());
			}
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			baos = new ByteArrayOutputStream();
			while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				baos.write(buff, 0, bytesRead);
			}
			bis.close();
			int i = 1;
			@SuppressWarnings("unused")
			String hdrKey = null;
			while ((hdrKey = conn.getHeaderFieldKey(i)) != null) {
				i++;
			}			
			return new String(baos.toByteArray(), "UTF-8");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return ""; // sw.toString();
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
}
