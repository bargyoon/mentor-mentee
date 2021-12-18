package com.kh.spring.member;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Mentor {

	@Id
	@GeneratedValue
	private Long mentorIdx;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="userIdx")
	private Member member;
	private String universityName;
	private String universityType;
	private int grade;
	private String major;
	private String wantDay;
	private String wantTime;
	private String requirement;
	private String history;
	@Column(columnDefinition = "number default 0")
	private int mentoringCnt;
	@Column(columnDefinition = "number default 0")
	private Boolean profileImg;
	private String accountNum;
	private String bank;
	
}
