package com.kh.spring.mentoring.model.dto;

public class Rating {

	private int ratingIdx;
	private int mentorIdx;
	private String kindness = "N";
	private String communication = "N";
	private String professional = "N";
	private String process = "N";
	private String appointment = "N";
	private String explain = "N";
	private String comment = "N";
	private int isDel;
	private int userIdx;
	
	public Rating() {
		// TODO Auto-generated constructor stub
	}

	public int getRatingIdx() {
		return ratingIdx;
	}

	public void setRatingIdx(int ratingIdx) {
		this.ratingIdx = ratingIdx;
	}

	public int getMentorIdx() {
		return mentorIdx;
	}

	public void setMentorIdx(int mentorIdx) {
		this.mentorIdx = mentorIdx;
	}

	public String getKindness() {
		return kindness;
	}

	public void setKindness(String kindness) {
		this.kindness = kindness;
	}

	public String getCommunication() {
		return communication;
	}

	public void setCommunication(String communication) {
		this.communication = communication;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	@Override
	public String toString() {
		return "Rating [ratingIdx=" + ratingIdx + ", mentorIdx=" + mentorIdx + ", kindness=" + kindness
				+ ", communication=" + communication + ", professional=" + professional + ", process=" + process
				+ ", appointment=" + appointment + ", explain=" + explain + ", comment=" + comment + ", isDel=" + isDel
				+ ", userIdx=" + userIdx + "]";
	}
	
	
	
}
