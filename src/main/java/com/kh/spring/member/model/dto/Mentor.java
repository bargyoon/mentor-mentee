package com.kh.spring.member.model.dto;

public class Mentor {

	private int mentorIdx;
	private int userIdx;
	private String universityName;
	private String universityType;
	private int grade;
	private String major;
	private String wantDay;
	private String wantTime;
	private String requirement;
	private String history;
	private int mentoringCnt;
	private int profileImg;
	private String accountNum;
	private String bank;
	
	public Mentor() {
		// TODO Auto-generated constructor stub
	}

	public int getMentorIdx() {
		return mentorIdx;
	}

	public void setMentorIdx(int mentorIdx) {
		this.mentorIdx = mentorIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getUniversityType() {
		return universityType;
	}

	public void setUniversityType(String universityType) {
		this.universityType = universityType;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getWantDay() {
		return wantDay;
	}

	public void setWantDay(String wantDay) {
		this.wantDay = wantDay;
	}

	public String getWantTime() {
		return wantTime;
	}

	public void setWantTime(String wantTime) {
		this.wantTime = wantTime;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public int getMentoringCnt() {
		return mentoringCnt;
	}

	public void setMentoringCnt(int mentoringCnt) {
		this.mentoringCnt = mentoringCnt;
	}

	public int getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(int profileImg) {
		this.profileImg = profileImg;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "Mentor [mentorIdx=" + mentorIdx + ", userIdx=" + userIdx + ", universityName=" + universityName
				+ ", universityType=" + universityType + ", grade=" + grade + ", major=" + major + ", wantDay="
				+ wantDay + ", wantTime=" + wantTime + ", requirement=" + requirement + ", history=" + history
				+ ", mentoringCnt=" + mentoringCnt + ", profileImg=" + profileImg + ", accountNum=" + accountNum
				+ ", bank=" + bank + "]";
	}


	

	

	
	
}
