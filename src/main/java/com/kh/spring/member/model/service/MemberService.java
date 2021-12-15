package com.kh.spring.member.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.common.http.HttpConnector;
import com.kh.spring.common.http.RequestParams;
import com.kh.spring.common.mail.MailSender;
import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentee;
import com.kh.spring.member.model.dto.Mentor;

public class MemberService {

	private MemberDao memberDao = new MemberDao();
	private JDBCTemplate template = JDBCTemplate.getInstance();

	public int insertMentee(Member member, Mentee mentee) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			// user에 정보 기입

			if (memberDao.insertMember(member, conn) != 0) {
				res = memberDao.insertMentee(mentee, conn);
				
			}

			// 회원가입 이후 자동 로그인 처리
			// 방금 가입한 회원의 정보를 다시 조회
			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 로그인처리 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}

		System.out.println(res);
		return res;

	}
	
	public int insertMentee(Member member, Mentee mentee, String kakaoId) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			// user에 정보 기입

			if (memberDao.insertMember(member, conn) != 0) {
				if( memberDao.insertMentee(mentee, conn) != 0) {
					res = memberDao.insertKakaoId(kakaoId, conn);
				}
			}

			// 회원가입 이후 자동 로그인 처리
			// 방금 가입한 회원의 정보를 다시 조회
			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 로그인처리 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}

		System.out.println(res);
		return res;

	}

	public int insertMentor(Member member, Mentor mentor) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			// user에 정보 기입

			if (memberDao.insertMember(member, conn) != 0) {
				res = memberDao.insertMentor(mentor, conn);
			}

			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 로그인처리 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}

		System.out.println(res);
		return res;
	}
	
	public int insertMentor(Member member, Mentor mentor, String kakaoId) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			// user에 정보 기입

			if (memberDao.insertMember(member, conn) != 0) {
				if( memberDao.insertMentor(mentor, conn) != 0) {
					res = memberDao.insertKakaoId(kakaoId, conn);
				}
			}

			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 로그인처리 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}

		System.out.println(res);
		return res;
	}
	
	
	public Member memberAuthenticate(String userId, String password) {
		Member member = null;

		Connection conn = template.getConnection();

		try {
			member = memberDao.memberAuthenticate(userId, password, conn);

		} finally {
			template.close(conn);
		}

		return member;
	}

	public Member selectMemberById(String userId) {
		Connection conn = template.getConnection();
		Member member = null;

		try {

			member = memberDao.selectMemberById(userId, conn);

		} finally {
			template.close(conn);
		}

		return member;
	}
	
	public int selectMemberByKakaoId(String kakaoId) {
		int userIdx = 0;
		Connection conn = template.getConnection();

		try {

			userIdx = memberDao.selectMemberByKakaoId(kakaoId, conn);

		} finally {
			template.close(conn);
		}

		return userIdx;
		
	}

	
	public Member selectMemberByPassword(String password) {
		Connection conn = template.getConnection();
		Member member = null;
		
		try {
			
			member = memberDao.selectMemberByPassword(password, conn);
			
		} finally {
			template.close(conn);
		}
		
		return member;
	}
	
	public Member selectMemberByEmail(String email) {
		Connection conn = template.getConnection();
		Member member = null;
		
		try {
			
			member = memberDao.selectMemberByEmail(email, conn);
			
		} finally {
			template.close(conn);
		}
		
		return member;
	}

	public Member selectMemberByIdx(int userIdx) {
		Connection conn = template.getConnection();
		Member member = null;

		try {

			member = memberDao.selectMemberByIdx(userIdx, conn);

		} finally {
			template.close(conn);
		}

		return member;
	}

	public Mentee selectMenteeByRole(Member member) {
		Connection conn = template.getConnection();
		Mentee mentee = null;

		try {

			mentee = memberDao.selectMenteeByRole(member.getUserIdx(), conn);

		} finally {
			template.close(conn);
		}

		return mentee;
	}

	public Mentor selectMentorByRole(Member member) {
		Connection conn = template.getConnection();
		Mentor mentor = null;

		try {

			mentor = memberDao.selectMentorByRole(member.getUserIdx(), conn);
		} finally {
			template.close(conn);
		}

		return mentor;
	}

	public int modifyMentor(Member member, Mentor mentor) {
		Connection conn = template.getConnection();
		int res = 0;
		try {

			if (memberDao.modifyMember(member, conn) != 0) {
				res = memberDao.modifyMentor(mentor, member.getUserIdx(), conn);
			}

			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 회원수정 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}
	
	public int modifyMentee(Member member, Mentee mentee) {
		Connection conn = template.getConnection();
		int res = 0;
		try {

			if (memberDao.modifyMember(member, conn) != 0) {
				res = memberDao.modifyMentee(mentee, member.getUserIdx(), conn);
			}

			// Member m = memberDao.selectMemberById(member.getUserId(), conn);
			// 다오를 통해 사용자 정보를 받아서 해당 정보로 로그인 처리 진행
			System.out.println(member.getUserId() + "의 회원수정 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}

	public void insertImg(Mentor mentor, List<FileDTO> fileDTOs) {
		Connection conn = template.getConnection();

		try {
			mentor = memberDao.selectMentorByRole(mentor.getUserIdx(), conn);

			// boardDao.insertBoard(board,conn);
			if (mentor.getProfileImg() == 0) {
				for (FileDTO fileDTO : fileDTOs) {
					memberDao.insertFile(fileDTO, mentor.getMentorIdx(), conn);
					mentor.setProfileImg(1);
					memberDao.modifyImg(1, mentor.getMentorIdx(), conn);

				}
			} else {
				for (FileDTO fileDTO : fileDTOs) {

					memberDao.deleteImg(mentor.getMentorIdx(), conn);

					memberDao.insertFile(fileDTO, mentor.getMentorIdx(), conn);

				}
			}

			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			System.out.println("close");
			template.close(conn);
		}

	}

	public FileDTO selectBoardDetail(int bdIdx) {

		Connection conn = template.getConnection();
		FileDTO file = null;

		try {

			file = memberDao.selectFileDTO(bdIdx, conn);
			

		} finally {
			template.close(conn);
		}

		return file;
	}
	
	public int modifyMember(Member member) {
		Connection conn = template.getConnection();
		int res = 0;
		try {

			res = memberDao.modifyMember(member, conn);
					
			System.out.println(member.getUserId() + "의 회원수정 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
		
		
	}
	
	public int kakaoAuth(Member member, String kakaoId) {
		Connection conn = template.getConnection();
		int res = 0;
		try {

			if(memberDao.modifyMember(member, conn) != 0) {
				res = memberDao.insertKakaoId(member.getUserIdx(),kakaoId, conn);
			}
					
			System.out.println(member.getUserId() + "의 회원수정 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
		
		
	}

	public int deleteMember(Member member) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			
			if(memberDao.modifyMember(member, conn) != 0) {
				res = memberDao.deleteKakaoId(member.getUserIdx(),conn);
			}
					
			System.out.println(member.getUserId() + "의 회원수정 로직이 동작했습니다.");
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			// 예전처럼 예외처리
			throw e;
		} finally {
			template.close(conn);
		}
		
		return res;
		
	}

	public void authenticateEmail(Member member, String persistToken) {

		HttpConnector conn = new HttpConnector();
		
		String queryString = conn.urlEncodedForm(RequestParams.builder() 
				.params("mail-template", "join-auth-mail")
				.params("persistToken", persistToken)
				.params("userName", member.getUserName())
				.build());
		
		
		
		
		String mailTemplate = conn.get("http://localhost:7070/mail?" + queryString);

		MailSender sender = new MailSender();

		sender.sendEmail(member.getEmail(), "환영합니다.", mailTemplate);

	}

	public void resetEmail(Member member, String persistToken) {
		HttpConnector conn = new HttpConnector();
		
		String queryString = conn.urlEncodedForm(RequestParams.builder() 
				.params("mail-template", "reset-password")
				.params("persistToken", persistToken)
				.params("email", member.getEmail())
				.build());
		
		
		
		
		String mailTemplate = conn.get("http://localhost:7070/mail?" + queryString);

		MailSender sender = new MailSender();

		sender.sendEmail(member.getEmail(), "비밀번호 변경 링크입니다.", mailTemplate);
		
	}

	public int setIsLeave(Member member) {
		int res = 0;
		
		Connection conn = template.getConnection();
		
		try {
			if(member.getRole().equals("MO00")) {
				res = memberDao.setIsLeaveMentor(member, conn);
			} else {
				res = memberDao.setIsLeaveMentee(member, conn);
			}
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}

	


	

	
	
}
