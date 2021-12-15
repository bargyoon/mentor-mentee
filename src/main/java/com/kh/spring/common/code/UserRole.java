package com.kh.spring.common.code;

public enum UserRole {

	MO00("일반멘토","mentor"),
	MO01("우수멘토","mentor"),
	ME00("mentee");
	
	
	public final String DESC;
	public final String ROLE;
	
	private UserRole(String desc, String role) {
		this.DESC = desc;
		this.ROLE = role;
	}

	private UserRole(String role) {
		this.DESC = "일반멘티";
		this.ROLE = role;
	
	}
	
}
