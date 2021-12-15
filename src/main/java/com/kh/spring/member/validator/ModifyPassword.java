package com.kh.spring.member.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.kh.spring.member.model.service.MemberService;

public class ModifyPassword {

	private String currPassword;
	private String newPassword;
	private String resetNewPassword;
	private String confirmPassword;
	private String resetConfirmPassword;
	private HttpServletRequest request;
	
	private MemberService memberService = new MemberService();
	
	private Map<String,String>failedValidation = new HashMap<String,String>();
	
	public ModifyPassword(ServletRequest request) {
		this.request = (HttpServletRequest) request;
		this.currPassword = request.getParameter("currPw");
		this.newPassword = request.getParameter("newPw");
		this.resetNewPassword = request.getParameter("resetNewPw");
		this.confirmPassword = request.getParameter("confirmNewPw");
		this.resetConfirmPassword = request.getParameter("resetConfirmNewPw");
		
	}
	
	public boolean test() {
		boolean isFailed = false;
		
		if(currPassword != null) {
			
			//사용자 비밀번호가 DB에 이미 존재하는 지 확인
			if(memberService.selectMemberByPassword(currPassword) == null || currPassword.equals("")) {
				failedValidation.put("currPassword",currPassword);
				isFailed = true;
			}
			//새로운 비밀번호가 이전 비밀번호와 같은지 확인
			if(newPassword.equals(currPassword)) {
				failedValidation.put("samePassword", newPassword);
				isFailed = true;
			}
			//비밀번호가 영어, 숫자, 특수문자 조합의 8자리 이상의 문자열인지 확인
			if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", newPassword)) {
				failedValidation.put("newPassword",newPassword);
				isFailed = true;
			}
			
			
			
			//비밀번호 확인이 비밀번호와 같은지 확인
			if(!confirmPassword.equals(newPassword)) {
				failedValidation.put("confirmPassword", confirmPassword);
				isFailed = true;
			}
		}else {
		
		//비밀번호가 영어, 숫자, 특수문자 조합의 8자리 이상의 문자열인지 확인
				if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", resetNewPassword)) {
					failedValidation.put("newPassword",resetNewPassword);
					isFailed = true;
				}
				
				
				
				//비밀번호 확인이 비밀번호와 같은지 확인
				if(!resetConfirmPassword.equals(resetNewPassword)) {
					failedValidation.put("confirmPassword", resetConfirmPassword);
					isFailed = true;
				}
		
		
		}
		
		
		if(isFailed) {
			request.getSession().setAttribute("modifyValid", failedValidation);
			request.getSession().setAttribute("modify", this);
			return false;
		}else {
			request.getSession().removeAttribute("modifyValid");
			request.getSession().removeAttribute("modify");
			return true;
		}
	}
}
