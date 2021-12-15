package com.kh.spring.board.mentor.model.dto;

import java.sql.Date;

public class BoardMentor {
	
	private int bdIdx;
	private String bdTitle;
	private Date regDate;
	private int viewCount;
	private int recCount;
	private String bdContent;
	private int mentorIdx;
	private String userId;
	private String detailDate;
	
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

	public String getBdTitle() {
		return bdTitle;
	}

	public void setBdTitle(String bdTitle) {
		this.bdTitle = bdTitle;
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
	

	public String getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(String detailDate) {
		this.detailDate = detailDate;
	}

	@Override
	public String toString() {
		return "BoardMentor [bdIdx=" + bdIdx + ", bdTitle=" + bdTitle + ", regDate=" + regDate + ", viewCount="
				+ viewCount + ", recCount=" + recCount + ", bdContent=" + bdContent + ", mentorIdx=" + mentorIdx
				+ ", userId=" + userId + ", detailDate=" + detailDate + "]";
	}

	

}
