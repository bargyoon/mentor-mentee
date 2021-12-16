package com.kh.spring.board;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kh.spring.member.Member;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Comment {

	@Id
	@GeneratedValue
	private Long coIdx;
	@JoinColumn(columnDefinition = "userId")
	private Member member;
	@JoinColumn(columnDefinition = "bdIdx")
	private Board board;
	private String coContent;
	@Column(columnDefinition = "date default sysdate")
	private LocalDate regDate;
	@Column(columnDefinition = "number default 0")
	private int recCount;
	
	
}
