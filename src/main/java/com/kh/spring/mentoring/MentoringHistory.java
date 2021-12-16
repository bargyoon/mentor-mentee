package com.kh.spring.mentoring;

import java.time.LocalDate;

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
public class MentoringHistory {

	@Id
	@GeneratedValue
	private int mentoringIdx;
	@ManyToOne
	@JoinColumn(columnDefinition = "userIdx")
	private Member member;
	@ManyToOne
	@JoinColumn(columnDefinition = "mentorIdx")
	private Mentor mentor;
	private String state;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate epDate;
	private int price;
	private String menteeName;
	

}
