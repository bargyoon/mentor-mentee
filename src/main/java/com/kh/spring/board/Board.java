package com.kh.spring.board;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
public class Board {

	@Id
	@GeneratedValue
	private Long bdIdx;
	private String bdTitle;
	@Column(columnDefinition = "date default sysdate")
	private LocalDate regDate;
	@Column(columnDefinition = "number default 0")
	private int viewCount;
	@Column(columnDefinition = "number default 0")
	private int recCount;
	private String bdContent;
	@ManyToOne
	@JoinColumn(name = "userIdx")
	private Member member;
	
}
