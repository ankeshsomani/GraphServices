package com.mastek.designschool.common.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CrossOrigin implements Filter {

	@Override
	public void destroy() {
	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse r = (HttpServletResponse) response;
	    String origin = ((HttpServletRequest) request).getHeader("origin");
	    r.addHeader("Access-Control-Allow-Origin", "*");
	    r.addHeader("Access-Control-Allow-Headers",
	            "userID,deviceID,tokenID,Origin,X-Requested-With,Accept,Accept-Encoding,Accept-Language,Cache-Control,"
	            + "Connection,Content-Length,Content-Type," +
	            "Cookie,Host,Pragma,Referer,RemoteQueueID,User-Agent");
	    r.addHeader("Access-Control-Allow-Credentials", "true");
	    //r.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	    r.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, HEAD");
	    chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	
	}

}
