package com.kh.spring.member;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Member {

	@Id
	@GeneratedValue
	private Long userIdx;
	private String userName;
	private String userId;
	private String password;
	private String email;
	private String gender;
	private String address;
	private String phone;
	private String nickname;
	private String role;
	@Column(columnDefinition = "date default sysdate")
	private LocalDate joinDate;
	@Column(columnDefinition = "number default 0")
	private Boolean isLeave;
	private String kakaoJoin;
}
