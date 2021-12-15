package com.kh.spring.member.model.dto;

import java.sql.Date;

public class Member {

	private int userIdx;
	private String userName;
	private String userId;
	private String password;
	private String email;
	private String gender;
	private String address;
	private String phone;
	private String nickname;
	private String role;
	private Date joinDate;
	private int isLeave;
	private String kakaoJoin;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public int getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}

	public String getKakaoJoin() {
		return kakaoJoin;
	}

	public void setKakaoJoin(String kakaoJoin) {
		this.kakaoJoin = kakaoJoin;
	}

	@Override
	public String toString() {
		return "Member [userIdx=" + userIdx + ", userName=" + userName + ", userId=" + userId + ", password=" + password
				+ ", email=" + email + ", gender=" + gender + ", address=" + address + ", phone=" + phone
				+ ", nickname=" + nickname + ", role=" + role + ", joinDate=" + joinDate + ", isLeave=" + isLeave
				+ ", kakaoJoin=" + kakaoJoin + "]";
	}

	
	
	
}
