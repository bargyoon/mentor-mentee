package com.kh.spring.mentoring.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentor;
import com.kh.spring.mentoring.model.dto.ApplyHistory;
import com.kh.spring.mentoring.model.dto.MentorCondition;
import com.kh.spring.mentoring.model.dto.MentoringHistory;
import com.kh.spring.mentoring.model.dto.Rating;

public class MentoringDao {

	JDBCTemplate template = JDBCTemplate.getInstance();
	
	public List<Mentor> getMentorByCondition(MentorCondition mentorCondition, Connection conn) {
		List<Mentor> mentorList = new ArrayList<Mentor>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = createConditionQuery(mentorCondition);
		
		try {
			pstm = conn.prepareStatement(query);
			
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				Mentor mentor = convertToMentor(rset);
				mentorList.add(mentor);
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return mentorList;
	}

	public Member getMemberByIdx(int userIdx, Connection conn) {
		Member member = new Member();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_idx = ? and is_leave = 0";
		
		try {
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				member = convertToMember(rset);
			}
			
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
		return member;
	}
	
	public List<MentoringHistory> getMtHistoryByUserIdx(int userIdx, String role, Connection conn) {
		List<MentoringHistory> mhList = new ArrayList<MentoringHistory>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from mentoring_history where user_idx = ? order by start_date desc";
		if(role.equals("MO00")) {
			query = "select * from mentoring_history where mentor_idx = ? order by start_date desc";
		}
		
		try {
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				MentoringHistory mh = convertToMhDTO(rset);
				mhList.add(mh);
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
		
		return mhList;
	}
	
	public List<ApplyHistory> getApHistoryByUserIdx(int userIdx, String role, Connection conn) {
		List<ApplyHistory> ahList = new ArrayList<ApplyHistory>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from apply_history where user_idx = ? order by apply_date desc";
		if(role.equals("MO00")) {
			query = "select * from apply_history where mentor_idx = ? order by apply_date desc";
		}
		
		try {
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				ApplyHistory ah = convertToApplyDTO(rset);
				ahList.add(ah);
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
		return ahList;
	}
	
	public int increaseReapplyCnt(int ahIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "update apply_history set reapply_cnt = reapply_cnt + 1, apply_date = SYSDATE, ep_date = SYSDATE+3 where a_idx = ? and reapply_cnt < 2";
	
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, ahIdx);
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
	
		return res;
	}
	
	public int registApply(ApplyHistory ah, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "insert into apply_history (a_idx, user_idx, mentor_name, mentor_idx, mentee_name) VALUES (SC_A_IDX.nextval, ?, ?, ?, ?)";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, ah.getUserIdx());
			pstm.setString(2, ah.getMentorName());
			pstm.setInt(3, ah.getMentorIdx());
			pstm.setString(4, ah.getMenteeName());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
	
	public boolean commentCheck(int mIdx, Connection conn) {
		boolean isRegistered = false;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select is_rating from mentoring_history where m_idx = ?";
		
		try {
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				if(rset.getInt("is_rating") == 1) {
					isRegistered = true;
				}
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return isRegistered;
	}
	
	public MentoringHistory getMhByMIdx(int mIdx, Connection conn) {
		MentoringHistory mh = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from mentoring_history where m_idx=?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				mh = convertToMhDTO(rset);
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return mh;
	}

	public int registRating(Rating rating, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "insert into rating (RATING_IDX, MENTOR_IDX, USER_IDX, KINDNESS, COMMUNICATION, PROFESSIONALISM, PROCESS, TIME_APPOINTMENT, EXPLAIN, USER_COMMENT)"
				+ " VALUES (SC_RATING_IDX.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, rating.getMentorIdx());
			pstm.setInt(2, rating.getUserIdx());
			pstm.setString(3, rating.getKindness());
			pstm.setString(4, rating.getCommunication());
			pstm.setString(5, rating.getProfessional());
			pstm.setString(6, rating.getProcess());
			pstm.setString(7, rating.getAppointment());
			pstm.setString(8, rating.getExplain());
			pstm.setString(9, rating.getComment());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		
		return res;
	}
	
	public List<Mentor> isExistInApply(List<Mentor> mentorList, int userIdx, Connection conn) {
		List<Mentor> nonExistMentor = new ArrayList<Mentor>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from user_mentor where mentor_idx not in (select mentor_idx from apply_history where user_idx = ?) and mentor_idx in (";
		for (int i = 0; i < mentorList.size(); i++) {
			if(i == mentorList.size()-1) {
				query += mentorList.get(i).getMentorIdx() + ")";
			} else {
				query += mentorList.get(i).getMentorIdx() + ",";
			}
		}
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				nonExistMentor.add(convertToMentor(rset));
			}
			
		} catch(SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return nonExistMentor;
	}
	
	public boolean isExistInApply(int mentorIdx, int userIdx, Connection conn) {
		boolean isExist = false;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from apply_history where mentor_idx = ? and user_idx = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorIdx);
			pstm.setInt(2, userIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				isExist = true;
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
		return isExist;
	}
	
	public int insertMH(MentoringHistory mh, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "insert into MENTORING_HISTORY (m_idx, mentor_idx, mentor_name, start_date, end_date, price, user_idx, mentee_name, state, ep_date)"
				+ " VALUES (SC_M_IDX.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		//util.Date -> sql.Date로 casting
		Date startDate = new Date(mh.getStartDate().getTime());
		Date endDate = new Date(mh.getEndDate().getTime());
		Date epDate = new Date(mh.getEpDate().getTime());
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mh.getMentorIdx());
			pstm.setString(2, mh.getMentorName());
			pstm.setDate(3, startDate);
			pstm.setDate(4, endDate);
			pstm.setInt(5, mh.getPrice());
			pstm.setInt(6, mh.getUserIdx());
			pstm.setString(7, mh.getMenteeName());
			pstm.setString(8, mh.getState());
			pstm.setDate(9, epDate);
			
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
	}

	public Mentor getMentorByUserIdx(int mentorUserIdx, Connection conn) {
		Mentor mentor = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from user_mentor where user_idx = ? and is_leave = 0";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorUserIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				mentor = convertToMentor(rset);
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
		return mentor;
	}

	public int deleteAH(int menteeIdx, int mentorIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "delete from apply_history where user_idx = ? and mentor_idx = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, menteeIdx);
			pstm.setInt(2, mentorIdx);
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
	}

	public int increaseMentoringCnt(int mentorIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "update user_mentor set mentoring_cnt = mentoring_cnt + 1 where mentor_idx = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorIdx);
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
	}

	public List<Rating> getRatingByMentorIdx(int mentorIdx, Connection conn) {
		List<Rating> mentorRating = new ArrayList<Rating>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from RATING where MENTOR_IDX = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorIdx);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				mentorRating.add(convertToRating(rset));
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return mentorRating;
	}
	
	public void deleteAh() {
		Connection conn = template.getConnection();
		
		PreparedStatement pstm = null;
		String query = "delete from apply_history where sysdate > ep_date";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.executeUpdate();
			template.commit(conn);
		} catch (SQLException e) {
			template.rollback(conn);
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
	}

	public void deleteMh() {
		Connection conn = template.getConnection();
		
		PreparedStatement pstm = null;
		String query = "delete from mentoring_history where sysdate > ep_date";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.executeUpdate();
			template.commit(conn);
		} catch (SQLException e) {
			template.rollback(conn);
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
	}
	
	public void updateMh() {
		Connection conn = template.getConnection();
		
		PreparedStatement pstm = null;
		String query = "update mentoring_history set state = 'F' where sysdate > ep_date";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.executeUpdate();
			template.commit(conn);
		} catch (SQLException e) {
			template.rollback(conn);
			new DataAccessException(e);
		} finally {
			template.close(pstm, conn);
		}
		
	}

	public FileDTO getFileByMentorIdx(int mentorIdx, Connection conn) {
		FileDTO file = new FileDTO();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select fl_idx, type_idx, origin_file_name, "
				+ " rename_file_name, save_path"
				+ " from file_info where type_idx = ? and is_del = 0";
		
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorIdx);
			
			rset = pstm.executeQuery();

			if(rset.next()) {
				file.setFlIdx(rset.getInt("fl_idx"));
				file.setTypeIdx(rset.getInt("type_idx"));
				file.setSavePath(rset.getString("save_path"));
				file.setOriginFileName(rset.getString("origin_file_name"));
				file.setRenameFileName(rset.getString("rename_file_name"));
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(rset, pstm);
		}
		
		return file;
		
	}

	public int modifyIsRating(int mIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "update mentoring_history set is_rating = 1 where m_idx = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mIdx);
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
	
	private String createQueryPart (String[] types) {
		String type = "";
		
		for (int i = 0; i < types.length; i++) {
			if(i == types.length-1) {
				type += "'" + types[i] + "')";
			} else {
				type += "'" + types[i] + "',";
			}
		}
		
		return type;
	}
	
	private String createConditionQuery (MentorCondition mentorCondition) {
		String query = "select * from user_mentor where";
		String universityType = " university_type in (";
		String wantTime = " want_time in (";
		String wantPlace = " requirement in (";
		String major = " major in (";
		String date = " want_day in (";
		String isLeave = " and is_leave = 0";
		
		universityType += createQueryPart(mentorCondition.getUniversityType());
		wantTime += createQueryPart(mentorCondition.getWantTime());
		wantPlace += createQueryPart(mentorCondition.getWantPlace());
		major += createQueryPart(mentorCondition.getMajorType());
		date += createQueryPart(mentorCondition.getWantDate());
		
		return query+universityType+" and"+wantTime+" and"+wantPlace+" and"+major+" and"+date + isLeave;
	}
	
	private Rating convertToRating(ResultSet rset) throws SQLException {
		Rating rating = new Rating();
		rating.setRatingIdx(rset.getInt("rating_idx"));
		rating.setMentorIdx(rset.getInt("mentor_idx"));
		rating.setKindness(rset.getString("kindness"));
		rating.setCommunication(rset.getString("COMMUNICATION"));
		rating.setProfessional(rset.getString("PROFESSIONALISM"));
		rating.setProcess(rset.getString("PROCESS"));
		rating.setAppointment(rset.getString("TIME_APPOINTMENT"));
		rating.setExplain(rset.getString("EXPLAIN"));
		rating.setComment(rset.getString("USER_COMMENT"));
		rating.setUserIdx(rset.getInt("user_idx"));
		
		return rating;
	}
	
	private ApplyHistory convertToApplyDTO(ResultSet rset) throws SQLException {
		ApplyHistory ah = new ApplyHistory();
		ah.setaIdx(rset.getInt("a_idx"));
		ah.setUserIdx(rset.getInt("user_idx"));
		ah.setMentorIdx(rset.getInt("mentor_idx"));
		ah.setMentorName(rset.getString("mentor_name"));
		ah.setApplyDate(rset.getDate("apply_date"));
		ah.setEpDate(rset.getDate("ep_date"));
		ah.setReapplyCnt(rset.getInt("REAPPLY_CNT"));
		ah.setMenteeName(rset.getString("mentee_name"));
		
		return ah;
	}
	
	private MentoringHistory convertToMhDTO(ResultSet rset) throws SQLException {
		MentoringHistory mh = new MentoringHistory();
		mh.setmIdx(rset.getInt("m_idx"));
		mh.setUserIdx(rset.getInt("user_idx"));
		mh.setMentorIdx(rset.getInt("mentor_idx"));
		mh.setMentorName(rset.getString("mentor_name"));
		mh.setStartDate(rset.getDate("start_date"));
		mh.setEndDate(rset.getDate("end_date"));
		mh.setPrice(rset.getInt("price"));
		mh.setState(rset.getString("state"));
		mh.setMenteeName(rset.getString("mentee_name"));

		return mh;
	}
	
	private Mentor convertToMentor(ResultSet rset) throws SQLException {
		Mentor mentor = new Mentor();
		mentor.setMentorIdx(rset.getInt("mentor_idx"));
		mentor.setUserIdx(rset.getInt("user_idx"));
		mentor.setUniversityName(rset.getString("UNIVERSITY_NAME"));
		mentor.setUniversityType(rset.getString("UNIVERSITY_TYPE"));
		mentor.setGrade(rset.getInt("GRADE"));
		mentor.setMajor(rset.getString("MAJOR"));
		mentor.setWantDay(rset.getString("WANT_DAY"));
		mentor.setWantTime(rset.getString("WANT_TIME"));
		mentor.setRequirement(rset.getString("REQUIREMENT"));
		mentor.setHistory(rset.getString("HISTORY"));
		mentor.setMentoringCnt(rset.getInt("MENTORING_CNT"));
		mentor.setProfileImg(rset.getInt("PROFILE_IMG"));
		mentor.setAccountNum(rset.getString("account_num"));
		mentor.setBank(rset.getString("bank"));
		return mentor;
	      
	}
	
	private Member convertToMember(ResultSet rset) throws SQLException {
		Member member = new Member();	
		member.setUserIdx(rset.getInt("user_idx"));
		member.setUserName(rset.getString("user_name"));
		member.setUserId(rset.getString("user_id"));
		member.setPassword(rset.getString("password"));
		member.setEmail(rset.getString("email"));
		member.setGender(rset.getString("gender"));
		member.setAddress(rset.getString("address"));
		member.setPhone(rset.getString("phone"));
		member.setNickname(rset.getString("nickname"));
		member.setRole(rset.getString("role"));
		member.setJoinDate(rset.getDate("join_date"));
		member.setIsLeave(rset.getInt("is_leave"));
		member.setKakaoJoin(rset.getString("kakao_join"));
		return member;

	}

	public Mentor getMentorByMentorIdx(int mentorIdx, Connection conn) {
		Mentor mentor = new Mentor();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from user_mentor where MENTOR_IDX = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, mentorIdx);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				mentor = convertToMentor(rset);
			}
			
		} catch (SQLException e) {
			new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return mentor;
	}


}
