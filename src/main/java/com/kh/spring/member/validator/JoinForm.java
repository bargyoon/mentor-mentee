package com.kh.spring.member.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.kh.spring.member.model.service.MemberService;

public class JoinForm {

	private String userName;
	private String userId;
	private String password;
	private String rePassword;
	private String email;
	private String address;
	private String phone;
	private String nickname;
	private HttpServletRequest request;

	private MemberService memberService = new MemberService();

	private Map<String, String> failedValidation = new HashMap<String, String>();

	public JoinForm(ServletRequest request) {
		this.request = (HttpServletRequest) request;
		this.userName = request.getParameter("userName");
		this.userId = request.getParameter("userId");
		this.password = request.getParameter("password");
		this.rePassword = request.getParameter("passwordConfirmation");
		this.email = request.getParameter("email");
		this.address = request.getParameter("address");
		this.phone = request.getParameter("phone");
		this.nickname = request.getParameter("nickname");
	}

	public boolean test() {
		boolean isFailed = false;

		// 사용자 아이디가 DB에 이미 존재하는 지 확인
		if (memberService.selectMemberById(userId) != null || userId.equals("")) {
			failedValidation.put("userId", userId);
			isFailed = true;
		}

		// 사용자 이메일이 DB에 이미 존재하는 지 확인
		if (memberService.selectMemberByEmail(email) != null || email.equals("")) {
			failedValidation.put("email", email);
			isFailed = true;
		}

		// 비밀번호가 영어, 숫자, 특수문자 조합의 8자리 이상의 문자열인지 확인
		if (!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", password)) {
			failedValidation.put("password", password);
			isFailed = true;
		}

		// 비밀번호 확인이 비밀번호와 같은지 확인
		if (!rePassword.equals(password)) {
			failedValidation.put("passwordConfirmation", rePassword);
			isFailed = true;
		}

		// 전화번호가 숫자로만 이루어져 있는 지 확인
		if (!Pattern.matches("\\d{6,8}", phone)) {
			failedValidation.put("phone", phone);
			isFailed = true;
		}

		if (isFailed) {
			request.getSession().setAttribute("joinValid", failedValidation);
			request.getSession().setAttribute("joinForm", this);
			return false;
		} else {
			request.getSession().removeAttribute("joinValid");
			request.getSession().removeAttribute("joinForm");
			return true;
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getNickname() {
		return nickname;
	}

}
