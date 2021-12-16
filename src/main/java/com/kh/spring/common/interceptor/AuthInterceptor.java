package com.kh.spring.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String[] uriArr = request.getRequestURI().split("/");

		if (uriArr.length != 0) {

			switch (uriArr[1]) {
			case "member":
				memberAuthorize(request, response, uriArr);
				break;
			case "admin":
				adminAuthorize(request, response, uriArr);
				break;
			case "board":
				boardAuthorize(request, response, uriArr);
				break;
			default:
				break;
			}

		}

		
		return true;
	}
	
	private void boardAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) throws IOException, ServletException{
	
	}

	private void adminAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr)
			throws IOException, ServletException {
		
	}


	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr)
			throws IOException, ServletException {

	}

}
