package com.kh.spring.common.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandlableException;
import com.kh.spring.member.model.dto.Member;



/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/AuthenticationFilter")
public class AuthorizationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthorizationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String[] uriArr = httpRequest.getRequestURI().split("/");
		
		if (uriArr.length != 0) {
		
			switch (uriArr[1]) {
			case "member":
				memberAuthorize(httpRequest, httpResponse, uriArr);
				break;
			
			case "board":
				boardAuthorize(httpRequest, httpResponse, uriArr);
				break;
			case "todo":
				todoListAuthorize(httpRequest, httpResponse, uriArr);
				break;
			case "mentoring":
				mentoringAuthorize(httpRequest, httpResponse, uriArr);
				break;
			default:
				break;
			}

		}

		chain.doFilter(request, response);
	}

	private void mentoringAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws IOException, ServletException{
		Member member = (Member) httpRequest.getSession().getAttribute("authentication");
		if(member == null){
			throw new HandlableException(ErrorCode.UNLOGINED_ERROR);
		}
		
		switch (uriArr[2]) {
		case "mentoring-accept":
			if(!member.getRole().equals("MO00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "regist-mentoring":
			if(!member.getRole().equals("MO00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "rating-page":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "mentor-list":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "regist-apply":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "apply-complete":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "apply-page":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		case "reapply-complete":
			if(!member.getRole().equals("ME00")) {
				throw new HandlableException(ErrorCode.ACCESS_ONLY_MENTOR);
			}
			break;
		default:
			break;
		}
		
	}

	private void todoListAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws IOException, ServletException {
		HttpSession session = httpRequest.getSession();
		if(session.getAttribute("authentication") == null){
			throw new HandlableException(ErrorCode.UNLOGINED_ERROR);
		}
		
	}

	private void boardAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws IOException, ServletException{
		HttpSession session = httpRequest.getSession();
		if(session.getAttribute("authentication") == null){
			throw new HandlableException(ErrorCode.UNLOGINED_ERROR);
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */

	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr)
			throws IOException, ServletException {
		HttpSession session = httpRequest.getSession();
		String serverToken = null;
		String clientToken = null;
		switch(uriArr[2]) {
		case "mypage": 
			if(session.getAttribute("authentication") == null){
				throw new HandlableException(ErrorCode.UNLOGINED_ERROR);
			}
			break;
		case "join-impl":
			serverToken = (String) httpRequest.getSession().getAttribute("persist-token");
			clientToken = httpRequest.getParameter("persist-token");

			if (serverToken == null || !serverToken.contentEquals(clientToken)) {
				throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
			}
			break;
		case "password-impl":
			serverToken = (String) httpRequest.getSession().getAttribute("persist-token");
			clientToken = httpRequest.getParameter("persist-token");

			if (serverToken == null || !serverToken.contentEquals(clientToken)) {
				throw new HandlableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
			}
			break;
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
