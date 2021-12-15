package com.kh.spring.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentee;
import com.kh.spring.member.model.dto.Mentor;

public class MemberDao {

	JDBCTemplate template = JDBCTemplate.getInstance();

	public Member memberAuthenticate(String userId, String password, Connection conn) {
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		try {
			String query = "select * from member where user_id = ? and password = ? and is_leave = 0 ";
			pstm = conn.prepareStatement(query);

			pstm.setString(1, userId);
			pstm.setString(2, password);
			rset = pstm.executeQuery();

			// 5. ResultSet에 저장된 데이터를 DTO에 옮겨담기.
			if (rset.next()) {
				member = convertRowToMember(rset);
			}

		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);

		}
		return member;
	}

	public Member selectMemberById(String userId, Connection conn) {
		Member member = null;

		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from member where user_id = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);

			rset = pstm.executeQuery();

			if (rset.next()) {
				member = convertRowToMember(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return member;
	}
	
	public Member selectMemberByPassword(String password, Connection conn) {
		Member member = null;

		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from member where password = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, password);

			rset = pstm.executeQuery();

			if (rset.next()) {
				member = convertRowToMember(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return member;
	}
	

	public Member selectMemberByIdx(int userIdx, Connection conn) {
		Member member = null;

		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from member where user_idx = ?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);

			rset = pstm.executeQuery();

			if (rset.next()) {
				member = convertRowToMember(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return member;
	}
	
	public Member selectMemberByEmail(String email, Connection conn) {
		Member member = null;

		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from member where email = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, email);

			rset = pstm.executeQuery();

			if (rset.next()) {
				member = convertRowToMember(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return member;
	}
	
	public int selectMemberByKakaoId(String kakaoId, Connection conn) {
		int userIdx = 0;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			
			String query = "select * from kakao_login where kakao_id = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, kakaoId);

			rset = pstm.executeQuery();

			if (rset.next()) {
				userIdx = rset.getInt("user_idx");
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return userIdx;
	}

	
	public List<Member> selectMemberList(Connection conn) {
		List<Member> memberList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from member";
			pstm = conn.prepareStatement(query);

			rset = pstm.executeQuery();

			while (rset.next()) {
				memberList.add(convertRowToMember(rset));
			}

		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return memberList;
	}

	public int insertMember(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "insert into member(user_idx,user_name,user_id,password,email,gender,address,phone,nickname,role,kakao_join) values(sc_user_idx.nextval,?,?,?,?,?,?,?,?,?,?)";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserName());
			pstm.setString(2, member.getUserId());
			pstm.setString(3, member.getPassword());
			pstm.setString(4, member.getEmail());
			pstm.setString(5, member.getGender());
			pstm.setString(6, member.getAddress());
			pstm.setString(7, member.getPhone());
			pstm.setString(8, member.getNickname());
			pstm.setString(9, member.getRole());
			pstm.setString(10, member.getKakaoJoin());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;

	}
	
	public int modifyMember(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update member set user_name=?, password=?, email=?, address=?, phone=?, is_leave=?, kakao_join=? where user_id = ?";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserName());
			pstm.setString(2, member.getPassword());
			pstm.setString(3, member.getEmail());
			pstm.setString(4, member.getAddress());
			pstm.setString(5, member.getPhone());
			pstm.setInt(6, member.getIsLeave());
			pstm.setString(7, member.getKakaoJoin());
			pstm.setString(8, member.getUserId());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}
	
	public int modifyMentor(Mentor mentor, int userIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update user_mentor set university_name = ?, grade = ?, major = ?, want_day = ?, want_time = ?, requirement = ?, history = ?, account_num =?, bank =? where user_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, mentor.getUniversityName());
			pstm.setInt(2, mentor.getGrade());
			pstm.setString(3, mentor.getMajor());
			pstm.setString(4, mentor.getWantDay());
			pstm.setString(5, mentor.getWantTime());
			pstm.setString(6, mentor.getRequirement());
			pstm.setString(7, mentor.getHistory());
			pstm.setString(8, mentor.getAccountNum());
			pstm.setString(9, mentor.getBank());
			pstm.setInt(10, userIdx);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}

	public int modifyMentee(Mentee mentee, int userIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update user_mentee set school_name = ?, major = ?, grade = ?, hope_university = ?, hope_major = ? where user_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, mentee.getSchoolName());
			pstm.setString(2, mentee.getMajor());
			pstm.setInt(3, mentee.getGrade());
			pstm.setString(4, mentee.getHopeUniversity());
			pstm.setString(5, mentee.getHopeMajor());
			pstm.setInt(6, userIdx);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}
	
	public int insertMentee(Mentee mentee, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "insert into user_mentee(mentee_idx,user_idx,school_name,major,grade,hope_university,hope_major) values(sc_mentee_idx.nextval,sc_user_idx.currval,?,?,?,?,?)";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, mentee.getSchoolName());
			pstm.setString(2, mentee.getMajor());
			pstm.setInt(3, mentee.getGrade());
			pstm.setString(4, mentee.getHopeUniversity());
			pstm.setString(5, mentee.getHopeMajor());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}

	public int insertMentor(Mentor mentor, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "insert into user_mentor(mentor_idx,user_idx,university_name,university_type,grade,major,want_day,want_time,requirement,history,account_num,bank) values(sc_mentor_idx.nextval,sc_user_idx.currval,?,?,?,?,?,?,?,?,?,?)";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, mentor.getUniversityName());
			pstm.setString(2, mentor.getUniversityType());
			pstm.setInt(3, mentor.getGrade());
			pstm.setString(4, mentor.getMajor());
			pstm.setString(5, mentor.getWantDay());
			pstm.setString(6, mentor.getWantTime());
			pstm.setString(7, mentor.getRequirement());
			pstm.setString(8, mentor.getHistory());
			pstm.setString(9, mentor.getAccountNum());
			pstm.setString(10, mentor.getBank());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}
	
	public int insertKakaoId(String kakaoId, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "insert into kakao_login(user_idx,kakao_id) values(sc_user_idx.currval,?)";

			pstm = conn.prepareStatement(query);
			pstm.setString(1, kakaoId);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
	}
	
	public int insertKakaoId(int userIdx, String kakaoId, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "insert into kakao_login(user_idx,kakao_id) values(?,?)";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			pstm.setString(2, kakaoId);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
		
	}
	
	public int deleteKakaoId(int userIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "delete from KAKAO_LOGIN where user_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

		return res;
		
	}


	public Mentee selectMenteeByRole(int userIdx, Connection conn) {
		Mentee mentee = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from user_mentee where user_idx = ?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();

			if (rset.next()) {
				mentee = convertRowToMentee(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return mentee;
	}
	
	public Mentor selectMentorByRole(int userIdx, Connection conn) {
		Mentor mentor = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			String query = "select * from user_mentor where user_idx = ?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, userIdx);
			rset = pstm.executeQuery();

			if (rset.next()) {
				mentor = convertRowToMentor(rset);
			}
		} catch (SQLException e) {

			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}

		return mentor;
	}

	public int insertFile(FileDTO fileDTO, int idx, Connection conn) {
		int res = 0;
		String sql = "insert into file_info"
				+ "(fl_idx,type_idx,origin_file_name,rename_file_name,save_path) "
				+ "values(sc_file_idx.nextval,?,?,?,?)";
		
		PreparedStatement pstm = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idx);
			pstm.setString(2, fileDTO.getOriginFileName());
			pstm.setString(3, fileDTO.getRenameFileName());
			pstm.setString(4, fileDTO.getSavePath());
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		return res;
		
	}
	
	public int deleteImg(int userIdx, Connection conn) {
		int res = 0;
		int i = 1;
		PreparedStatement pstm = null;

		try {
			String query = "update file_info set is_del = ? where type_idx =  ?";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, i);
			pstm.setInt(2, userIdx);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
		return res;
		
	}
	
	
	public int modifyImg(int i, int mentorIdx, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update user_mentor set profile_img = ? where mentor_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, i);
			pstm.setInt(2, mentorIdx);

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
		
	}
	
public FileDTO selectFileDTO(int bdIdx, Connection conn) {
		
		String sql = "select fl_idx, type_idx, origin_file_name, "
				+ " rename_file_name, save_path"
				+ " from file_info where type_idx = ? and is_del = 0";
		
		PreparedStatement pstm = null;
		ResultSet rset = null;
		FileDTO file = new FileDTO();
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			
			rset = pstm.executeQuery();

			while(rset.next()) {
				
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
	
	private Member convertRowToMember(ResultSet rset) throws SQLException {
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

	private Mentor convertRowToMentor(ResultSet rset) throws SQLException {
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

	private Mentee convertRowToMentee(ResultSet rset) throws SQLException {
		Mentee mentee = new Mentee();
		mentee.setMenteeIdx(rset.getInt("MENTEE_IDX"));
		mentee.setUserIdx(rset.getInt("user_idx"));
		mentee.setSchoolName(rset.getString("SCHOOL_NAME"));
		mentee.setMajor(rset.getString("MAJOR"));
		mentee.setGrade(rset.getInt("GRADE"));
		mentee.setHopeUniversity(rset.getString("HOPE_UNIVERSITY"));
		mentee.setHopeMajor(rset.getString("HOPE_MAJOR"));
		return mentee;
	}

	public int setIsLeaveMentor(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update user_mentor set is_leave = 1 where user_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, member.getUserIdx());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}

	public int setIsLeaveMentee(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;

		try {
			String query = "update user_mentee set is_leave = 1 where user_idx = ?";

			pstm = conn.prepareStatement(query);
			pstm.setInt(1, member.getUserIdx());

			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}

	
	

}
