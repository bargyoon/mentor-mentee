package com.kh.spring.member;

import javax.persistence.CascadeType;
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
public class Mentee {

	@Id
	@GeneratedValue
	private Long menteeIdx;
	
	@OneToOne
	@JoinColumn(name = "userIdx")
	private Member member;
	private String schoolName;
	private String major;
	private int grade;
	private String hopeUniversity;
	private String hopeMajor;
}
