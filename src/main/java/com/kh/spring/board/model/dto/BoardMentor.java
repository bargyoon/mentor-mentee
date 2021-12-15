package com.kh.spring.board.model.dto;

import java.sql.Date;

public class BoardMentor {
	
	private int bdIdx;
	private String title;
	private Date regDate;
	private int viewCount;
	private int recCount;
	private String bdContent;
	private int mentorIdx;
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BoardMentor() {
		// TODO Auto-generated constructor stub
	}

	public int getBdIdx() {
		return bdIdx;
	}

	public void setBdIdx(int bdIdx) {
		this.bdIdx = bdIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getRecCount() {
		return recCount;
	}

	public void setRecCount(int recCount) {
		this.recCount = recCount;
	}

	public String getBdContent() {
		return bdContent;
	}

	public void setBdContent(String bdContent) {
		this.bdContent = bdContent;
	}

	public int getMentorIdx() {
		return mentorIdx;
	}

	public void setMentorIdx(int mentorIdx) {
		this.mentorIdx = mentorIdx;
	}

	@Override
	public String toString() {
		return "BoardMentor [bdIdx=" + bdIdx + ", title=" + title + ", regDate=" + regDate + ", viewCount=" + viewCount
				+ ", recCount=" + recCount + ", bdContent=" + bdContent + ", mentorIdx=" + mentorIdx + ", userId="
				+ userId + "]";
	}

	

}
