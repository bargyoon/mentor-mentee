package com.kh.spring.mentoring.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.common.http.HttpConnector;
import com.kh.spring.common.http.RequestParams;
import com.kh.spring.common.mail.MailSender;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentor;
import com.kh.spring.mentoring.model.dao.MentoringDao;
import com.kh.spring.mentoring.model.dto.ApplyHistory;
import com.kh.spring.mentoring.model.dto.MentorCondition;
import com.kh.spring.mentoring.model.dto.MentoringHistory;
import com.kh.spring.mentoring.model.dto.Rating;

public class MentoringService {
	
	private MentoringDao mDao = new MentoringDao();
	private JDBCTemplate template = JDBCTemplate.getInstance();
	
	public List<Mentor> getMentorByCondition(MentorCondition mentorCondition) {
		List<Mentor> mentorList = new ArrayList<Mentor>();
		
		Connection conn = template.getConnection();
		
		try {
			
			mentorList = mDao.getMentorByCondition(mentorCondition, conn);
			
		} finally {
			template.close(conn);
		}
		
		return mentorList;
	}

	public Member getMemberByIdx(int userIdx) {
		Member memberList = new Member();
		
		Connection conn = template.getConnection();
		
		try {
			memberList = mDao.getMemberByIdx(userIdx, conn);
		} finally {
			template.close(conn);
		}
		return memberList;
	}

	public List<MentoringHistory> getMtHistoryByUserIdx(int userIdx, String role) {
		List<MentoringHistory> mhList = new ArrayList<MentoringHistory>();
		
		Connection conn = template.getConnection();
		
		try {
			
			mhList = mDao.getMtHistoryByUserIdx(userIdx, role, conn);
			
		} finally {
			template.close(conn);
		}
		
		return mhList;
	}

	public List<ApplyHistory> getApHistoryByUserIdx(int userIdx, String role) {
		List<ApplyHistory> ahList = new ArrayList<ApplyHistory>();
		
		Connection conn = template.getConnection();
		
		try {
			
			ahList = mDao.getApHistoryByUserIdx(userIdx, role, conn);
			
		} finally {
			template.close(conn);
		}
		
		return ahList;
	}

	public int increaseReapplyCnt(int ahIdx) {
		int res = 0;
		
		Connection conn = template.getConnection();
		
		try {
			res = mDao.increaseReapplyCnt(ahIdx, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		return res;
	}

	public int registApply(ApplyHistory ah) {
		int res = 0;
		
		Connection conn = template.getConnection();
		
		try {
			res = mDao.registApply(ah, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		
		return res;
	}

	public boolean commentCheck(int mIdx) {
		boolean isRegistered = false;
		
		Connection conn = template.getConnection();
		
		try {
			isRegistered = mDao.commentCheck(mIdx, conn);
		} finally {
			template.close(conn);
		}
		
		return isRegistered;
	}

	public MentoringHistory getMhByMIdx(int mIdx) {
		MentoringHistory mh = null;
		
		Connection conn = template.getConnection();
		
		try {
			mh = mDao.getMhByMIdx(mIdx, conn);
		} finally {
			template.close(conn);
		}
		
		return mh;
	}

	public int registRating(Rating rating, int mIdx) {
		int res = 0;
		
		Connection conn = template.getConnection();
		
		try {
			res = mDao.registRating(rating, conn);
			res = mDao.modifyIsRating(mIdx, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			e.printStackTrace();
		} finally {
			template.close(conn);
		}
		
		return res;
	}

	public List<Mentor> isExistInApply(List<Mentor> mentorList, int userIdx) {
		List<Mentor> nonExistMentor = new ArrayList<Mentor>();
		
		Connection conn = template.getConnection();
		
		try {
			nonExistMentor = mDao.isExistInApply(mentorList, userIdx, conn);
		} finally {
			template.close(conn);
		}
		
		return nonExistMentor;
	}

	public boolean isExistInApply(int mentorIdx, int userIdx) {
		boolean isExist = false;
		Connection conn = template.getConnection();
		
		try {
			isExist = mDao.isExistInApply(mentorIdx, userIdx, conn);
		} finally {
			template.close(conn);
		}
		return isExist;
	}

	public Mentor getMentorByUserIdx(int mentorUserIdx) {
		Mentor mentor = null;
		
		Connection conn = template.getConnection();
		
		try {
			mentor = mDao.getMentorByUserIdx(mentorUserIdx,conn);
		} finally {
			template.close(conn);
		}
		
		return mentor;
	}

	public int insertMH(MentoringHistory mh) {
		int res = 0;
		
		Connection conn = template.getConnection();
		
		try {
			res = mDao.insertMH(mh, conn);
			template.commit(conn);
		} catch(Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}

	public int deleteAH(int menteeIdx, int mentorIdx) {
		int res = 0;
		Connection conn = template.getConnection();
		
		try {
			res = mDao.deleteAH(menteeIdx, mentorIdx, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		return res;
	}

	public int increaseMentoringCnt(int mentorIdx) {
		int res = 0;
		Connection conn = template.getConnection();
		
		try {
			res = mDao.increaseMentoringCnt(mentorIdx, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
		return res;
	}

	public List<Rating> getRatingByMentorIdx(int mentorIdx) {
		List<Rating> mentorRating = new ArrayList<Rating>();
		Connection conn = template.getConnection();
		
		try {
			mentorRating = mDao.getRatingByMentorIdx(mentorIdx, conn);
		} finally {
			template.close(conn);
		}
		
		return mentorRating;
	}

	public Mentor getMentorByMentorIdx(int mentorIdx) {
		Mentor mentor = new Mentor();
		Connection conn = template.getConnection();
		
		try {
			mentor = mDao.getMentorByMentorIdx(mentorIdx, conn);
		} finally {
			template.close(conn);
		}
		
		return mentor;
	}

	public FileDTO getFileByMentorIdx(int mentorIdx) {
		Connection conn = template.getConnection();
		
		FileDTO file = null;

		try {

			file = mDao.getFileByMentorIdx(mentorIdx, conn);
			

		} finally {
			template.close(conn);
		}

		return file;
	}

	public void sendEmailToMentor(Member member) {
		HttpConnector conn = new HttpConnector();
		
		String queryString = conn.urlEncodedForm(RequestParams.builder() 
				.params("mail-template", "notice-apply")
				.params("email", member.getEmail())
				.build());
		
		String mailTemplate = conn.get("http://localhost:7070/mail?" + queryString);

		MailSender sender = new MailSender();

		sender.sendEmail(member.getEmail(), "멘토 신청 완료 메일입니다.", mailTemplate);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
