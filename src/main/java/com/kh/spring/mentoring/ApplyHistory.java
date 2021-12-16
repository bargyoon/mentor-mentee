package com.kh.spring.mentoring;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.member.Member;
import com.kh.spring.member.Mentor;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class ApplyHistory {

	@Id
	@GeneratedValue
	private int applyIdx;
	@ManyToOne
	@JoinColumn(columnDefinition = "userIdx")
	private Member member;
	@ManyToOne
	@JoinColumn(columnDefinition = "mentorIdx")
	private Mentor mentor;
	@Column(columnDefinition = "date default sysdate")
	private Date applyDate;
	@Column(columnDefinition = "date default sysdate")
	private Date epDate;
	@Column(columnDefinition = "number default 0")
	private int reapplyCnt;
	


}
