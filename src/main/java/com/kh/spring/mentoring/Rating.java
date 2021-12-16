package com.kh.spring.mentoring;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kh.spring.member.Member;
import com.kh.spring.member.Mentor;

public class Rating {

	@Id
	@GeneratedValue
	private int ratingIdx;
	@ManyToOne
	@JoinColumn(columnDefinition = "mentorIdx")
	private Mentor mentor;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean kindness;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean communication;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean professional;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean process;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean appointment;
	@Column(columnDefinition = "varchar2(1) default N")
	private Boolean explain;
	private String comment;
	@Column(columnDefinition = "number default 0")
	private Boolean isDel;
	@ManyToOne
	@JoinColumn(columnDefinition = "userIdx")
	private Member member;
	
	
	
	
}
