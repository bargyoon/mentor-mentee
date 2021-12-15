package com.kh.spring.common.code;

public enum Config {

UPLOAD_PATH("C:\\CODE\\E_SERVLET\\mm_project\\WebContent\\resources\\img\\"), //개발서버
	SMTP_AUTHENTIFICATION_ID("bargyoon@gmail.com"),
	SMTP_AUTHENTIFICATION_PASSWORD("147856aa"),
	COMPANY_EMAIL("bargyoon@gmail.com");
	
	
	public final String DESC;
	
	private Config(String desc) {
		this.DESC = desc;
	}
	
	
	
	
}
