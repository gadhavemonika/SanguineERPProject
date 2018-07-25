package com.sanguine.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class clsRequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*
		 * System.out.println("request.getRequestURL()="+request.getRequestURL())
		 * ;
		 * System.out.println("request.getContextPath()="+request.getContextPath
		 * ());
		 * System.out.println("request.getRequestURI()="+request.getRequestURI
		 * ());
		 */
		String prjName = request.getContextPath().toString();
		prjName = prjName.concat("/");
		if (!request.getRequestURI().equals(prjName) && !request.getRequestURI().equals(prjName + "index.html") && !request.getRequestURI().equals(prjName + "validateUser.html") && !request.getRequestURI().equals(prjName + "validateClient.html") && !request.getRequestURI().equals(prjName + "logout.html") && !request.getRequestURI().equals(prjName + "updateStructure.html")) {

			if (null == request.getSession().getAttribute("usercode")) {
				response.sendRedirect(prjName);
				return false;
			}

		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}
