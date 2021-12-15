package com.kh.spring.member.model.dto;

public class Mentee {

	private int menteeIdx;
	private int userIdx;
	private String schoolName;
	private String major;
	private int grade;
	private String hopeUniversity;
	private String hopeMajor;
	
	public Mentee() {
		// TODO Auto-generated constructor stub
	}

	public int getMenteeIdx() {
		return menteeIdx;
	}

	public void setMenteeIdx(int menteeIdx) {
		this.menteeIdx = menteeIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getHopeUniversity() {
		return hopeUniversity;
	}

	public void setHopeUniversity(String hopeUniversity) {
		this.hopeUniversity = hopeUniversity;
	}

	public String getHopeMajor() {
		return hopeMajor;
	}

	public void setHopeMajor(String hopeMajor) {
		this.hopeMajor = hopeMajor;
	}

	@Override
	public String toString() {
		return "Mentee [menteeIdx=" + menteeIdx + ", userIdx=" + userIdx + ", schoolName=" + schoolName + ", major="
				+ major + ", grade=" + grade + ", hopeUniversity=" + hopeUniversity + ", hopeMajor=" + hopeMajor + "]";
	}
	
	
}
