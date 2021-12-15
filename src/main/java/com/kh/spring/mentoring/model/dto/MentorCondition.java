package com.kh.spring.mentoring.model.dto;

import java.util.Arrays;

public class MentorCondition {
	private String[] universityType;
	private String[] wantTime;
	private String[] wantPlace;
	private String[] majorType;
	private String[] wantDate;
	
	public MentorCondition() {
		// TODO Auto-generated constructor stub
	}

	public String[] getUniversityType() {
		return universityType;
	}

	public void setUniversityType(String[] universityType) {
		this.universityType = universityType;
	}

	public String[] getWantTime() {
		return wantTime;
	}

	public void setWantTime(String[] wantTime) {
		this.wantTime = wantTime;
	}

	public String[] getWantPlace() {
		return wantPlace;
	}

	public void setWantPlace(String[] wantPlace) {
		this.wantPlace = wantPlace;
	}

	public String[] getMajorType() {
		return majorType;
	}

	public void setMajorType(String[] majorType) {
		this.majorType = majorType;
	}

	public String[] getWantDate() {
		return wantDate;
	}

	public void setWantDate(String[] wantDate) {
		this.wantDate = wantDate;
	}

	@Override
	public String toString() {
		return "MentorCondition [universityType=" + Arrays.toString(universityType) + ", wantTime="
				+ Arrays.toString(wantTime) + ", wantPlace=" + Arrays.toString(wantPlace) + ", majorType="
				+ Arrays.toString(majorType) + ", wantDate=" + Arrays.toString(wantDate) + "]";
	}


	
	
}
