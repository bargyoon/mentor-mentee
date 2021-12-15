package com.kh.spring.todo.model.dto;

import java.sql.Date;

public class Todo {
/*
	TODO_IDX
	USER_IDX
	START_DATE
	END_DATE
	TITLE
	DONE
	COLOR
*/	
	
	private int todoIdx;
	private int userIdx;
	private Date startDate;
	private Date endDate;
	private String start;
	private String end;
	private String title;
	private String done;
	private String color;
	
	public Todo() {
		// TODO Auto-generated constructor stub
	}

	public int getTodoIdx() {
		return todoIdx;
	}

	public void setTodoIdx(int todoIdx) {
		this.todoIdx = todoIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDone() {
		return done;
	}

	public void setDone(String done) {
		this.done = done;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Todo [todoIdx=" + todoIdx + ", userIdx=" + userIdx + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", title=" + title + ", done=" + done + ", color=" + color + "]";
	}


	
}	