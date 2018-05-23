package com.yhao.demo.commons.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//过滤器
public class MyFilter implements Filter {
	
	/*
	 * 1、实现Filter[javax.servlet.Filter]接口，实现Filter方法
	 * 2、添加@Configuration注解，将自定义Filter加入过滤链
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("" + this.getClass() + " destroy");
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) srequest;
		logger.info("this is MyFilter,url:" + request.getRequestURI());
		chain.doFilter(srequest, sresponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("" + this.getClass() + " init");
	}
	
}
