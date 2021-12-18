package com.kh.spring.todo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.member.Member;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Todo {

	@Id
	@GeneratedValue
	private Long todoIdx;
	@ManyToOne
	@JoinColumn(name = "userIdx")
	private Member member;
	private LocalDate startDate;
	private LocalDate endDate;
	private String title;
	@Column(columnDefinition = "number default 0")
	private Boolean isDone;
	private String todoColor;
	
	
}
