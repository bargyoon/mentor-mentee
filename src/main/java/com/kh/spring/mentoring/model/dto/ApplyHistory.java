package com.kh.spring.mentoring.model.dto;

import java.util.Date;

public class ApplyHistory {

	private int aIdx;
	private int userIdx;
	private int mentorIdx;
	private String mentorName;
	private Date applyDate;
	private Date epDate;
	private int reapplyCnt;
	private String menteeName;
	
	public ApplyHistory() {
		// TODO Auto-generated constructor stub
	}

	public int getaIdx() {
		return aIdx;
	}

	public void setaIdx(int aIdx) {
		this.aIdx = aIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getMentorIdx() {
		return mentorIdx;
	}

	public void setMentorIdx(int mentorIdx) {
		this.mentorIdx = mentorIdx;
	}

	public String getMentorName() {
		return mentorName;
	}

	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getEpDate() {
		return epDate;
	}

	public void setEpDate(Date epDate) {
		this.epDate = epDate;
	}

	public int getReapplyCnt() {
		return reapplyCnt;
	}

	public void setReapplyCnt(int reapplyCnt) {
		this.reapplyCnt = reapplyCnt;
	}
	
	public String getMenteeName() {
		return menteeName;
	}

	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}

	@Override
	public String toString() {
		return "ApplyHistory [aIdx=" + aIdx + ", userIdx=" + userIdx + ", mentorIdx=" + mentorIdx + ", mentorName="
				+ mentorName + ", applyDate=" + applyDate + ", epDate=" + epDate + ", reapplyCnt=" + reapplyCnt
				+ ", menteeName=" + menteeName + "]";
	}


}
