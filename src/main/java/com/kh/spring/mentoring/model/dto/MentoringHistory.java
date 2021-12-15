package com.kh.spring.mentoring.model.dto;

import java.util.Date;

public class MentoringHistory {

	private int mIdx;
	private int userIdx;
	private int mentorIdx;
	private String mentorName;
	private String state;
	private Date startDate;
	private Date endDate;
	private Date epDate;
	private int price;
	private String menteeName;
	
	public MentoringHistory() {
		// TODO Auto-generated constructor stub
	}

	public int getmIdx() {
		return mIdx;
	}

	public void setmIdx(int mIdx) {
		this.mIdx = mIdx;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEpDate() {
		return epDate;
	}

	public void setEpDate(Date epDate) {
		this.epDate = epDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getMenteeName() {
		return menteeName;
	}

	public void setMenteeName(String menteeName) {
		this.menteeName = menteeName;
	}

	@Override
	public String toString() {
		return "MentoringHistory [mIdx=" + mIdx + ", userIdx=" + userIdx + ", mentorIdx=" + mentorIdx + ", mentorName="
				+ mentorName + ", state=" + state + ", startDate=" + startDate + ", endDate=" + endDate + ", epDate="
				+ epDate + ", price=" + price + ", menteeName=" + menteeName + "]";
	}
	
}
