package com.kh.spring.board.mentor.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.board.mentor.model.dto.BoardComment;
import com.kh.spring.board.mentor.model.dto.BoardMentor;
import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;

public class BoardDao {

	private JDBCTemplate template = JDBCTemplate.getInstance();

	public List<BoardMentor> selectBoardList(String searchCondition, String searchKeyWord, int page, Connection conn) {
		List<BoardMentor> boardMentorList = new ArrayList<BoardMentor>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from ("
				+ "select rownum num, A.*"
				+ " from (select * from mentor_board where "+ searchCondition +" like ? order by reg_date desc) A"
				+ ") "
				+ "where num between ? and ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+searchKeyWord+"%");
			pstm.setInt(2, 1+(page-1)*15);
			pstm.setInt(3,page*15);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMentorList.add(convertRowToBoardList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMentorList;
	}
	
	public int getBoardCount() {
		return 0;
	}
	
	public int getBoardCount(String searchCondition, String searchKeyWord, Connection conn) {
		
		int count = 0;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		String sql = "select count(bd_idx) count from ("
				+ "select rownum num, A.*"
				+ "from (select * from mentor_board where "+ searchCondition +" like ? order by reg_date desc) A"
				+ ") ";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+searchKeyWord+"%");
			
			rset = pstm.executeQuery();
			
			if(rset.next()) {
			count = rset.getInt("count");
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		
		return count;
	}
	
	public BoardMentor getNextBoard(int bdIdx) {
		
		String sql = "select * from mentor_board "
				+ "where bd_idx = ("
				+ "select bd_idx from mentor_board "
				+ "where reg_date > (select reg_date from mentor_board where bd_idx=3) "
				+ "and rownum = 1"
				+ ")";
		
		return null;
	}
	
	public BoardMentor getPrevBoard(int bdIdx) {
		
		String sql = "select bd_idx from ("
				+ "select * from mentor_board order by reg_date desc) "
				+ "where reg_date < (select reg_date from mentor_board where bd_idx = 3 "
				+ "and rownum = 1";
		
		return null;
	}

	public List<FileDTO> selectFileDTOs(int bdIdx, Connection conn) {

		String sql = "select fl_idx, type_idx, origin_file_name, " + " rename_file_name, save_path"
				+ " from file_info where type_idx = ?";

		PreparedStatement pstm = null;
		ResultSet rset = null;
		List<FileDTO> files = new ArrayList<FileDTO>();

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);

			rset = pstm.executeQuery();

			while (rset.next()) {
				FileDTO fileDTO = new FileDTO();
				fileDTO.setFlIdx(rset.getInt("fl_idx"));
				fileDTO.setTypeIdx(rset.getInt("type_idx"));
				fileDTO.setSavePath(rset.getString("save_path"));
				fileDTO.setOriginFileName(rset.getString("origin_file_name"));
				fileDTO.setRenameFileName(rset.getString("rename_file_name"));
				files.add(fileDTO);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return files;
	}

	public BoardMentor selectBoardDetail(int bdIdx, Connection conn) {
		BoardMentor boardMentorDetail = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select bd_idx,bd_title,reg_date,view_count,rec_count,bd_content,mentor_idx,user_id,detail_date "
				+ "from mentor_board where bd_idx=?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMentorDetail = convertRowToBoardList(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMentorDetail;
	}

	public void insertBoard(BoardMentor boardMentor, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "insert into mentor_board(bd_idx,bd_title,reg_date,view_count,rec_count,bd_content,mentor_idx,user_id,detail_date)"
				+ "values(sc_mentor_bd_idx.nextval,?,sysdate,0,0,?,?,?,to_char(current_timestamp,'yyyy-mm-dd hh24:mi:ss'))";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, boardMentor.getBdTitle());
			pstm.setString(2, boardMentor.getBdContent());
			pstm.setInt(3, boardMentor.getMentorIdx());
			pstm.setString(4, boardMentor.getUserId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
	}

	private BoardMentor convertRowToBoardList(ResultSet rset) throws SQLException {

		BoardMentor boardMentor = new BoardMentor();
		boardMentor.setBdIdx(rset.getInt("bd_idx"));
		boardMentor.setBdTitle(rset.getString("bd_title"));
		boardMentor.setRegDate(rset.getDate("reg_date"));
		boardMentor.setViewCount(rset.getInt("view_count"));
		boardMentor.setRecCount(rset.getInt("rec_count"));
		boardMentor.setBdContent(rset.getString("bd_content"));
		boardMentor.setMentorIdx(rset.getInt("mentor_idx"));
		boardMentor.setUserId(rset.getString("user_id"));
		boardMentor.setDetailDate(rset.getString("detail_date"));

		return boardMentor;
	}

	public void deleteBoard(int bdIdx, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "delete from mentor_board where bd_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

	}

	public void deleteBoardFileDatas(int bdIdx, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "delete from file_info where type_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

	}

	public void updateBoard(BoardMentor boardMentor, int bdIdx, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "update mentor_board set bd_title = ?, bd_content = ?, reg_date = sysdate where bd_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, boardMentor.getBdTitle());
			pstm.setString(2, boardMentor.getBdContent());
			pstm.setInt(3, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

	}

	public void insertFile(FileDTO fileDTO, Connection conn) {

		String sql = "insert into file_info" + "(fl_idx,type_idx,origin_file_name,rename_file_name,save_path) "
				+ "values(sc_file_idx.nextval,sc_mentor_bd_idx.currval,?,?,?)";

		PreparedStatement pstm = null;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, fileDTO.getOriginFileName());
			pstm.setString(2, fileDTO.getRenameFileName());
			pstm.setString(3, fileDTO.getSavePath());
			pstm.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}

	}

	public void insertFile(FileDTO fileDTO, Connection conn, int bdIdx) {
		
		String sql = "insert into file_info" + "(fl_idx,type_idx,origin_file_name,rename_file_name,save_path) "
				+ "values(sc_file_idx.nextval,?,?,?,?)";

		PreparedStatement pstm = null;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.setString(2, fileDTO.getOriginFileName());
			pstm.setString(3, fileDTO.getRenameFileName());
			pstm.setString(4, fileDTO.getSavePath());
			pstm.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
	}

	public void updateViewCount(int bdIdx, Connection conn) {
		
		PreparedStatement pstm = null;
		String sql = "update mentor_board set view_count = (select view_count+1 from mentor_board where bd_idx = ?) where bd_idx = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.setInt(2, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
	}

	public void insertComment(BoardComment boardComment, int bdIdx, Connection conn) {
		
		PreparedStatement pstm = null;

		String sql = "insert into mentor_comment(co_idx,user_id,co_content,reg_date,rec_count,bd_idx,detail_date)"
				+ "values(sc_mentor_co_idx.nextval,?,?,sysdate,0,?,to_char(current_timestamp,'yyyy-mm-dd hh24:mi:ss'))";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, boardComment.getUserId());
			pstm.setString(2, boardComment.getCoContent());
			pstm.setInt(3, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
	}

	public List<BoardComment> selectBoardComment(int bdIdx, Connection conn) {
		
		List<BoardComment> boardMentorCommentList = new ArrayList<BoardComment>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from mentor_comment where bd_idx = ? order by co_idx desc";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMentorCommentList.add(convertRowToBoardCommentList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMentorCommentList;
	}

	private BoardComment convertRowToBoardCommentList(ResultSet rset) throws SQLException {
		
		BoardComment boardComment = new BoardComment();
		boardComment.setCoIdx(rset.getInt("co_idx"));
		boardComment.setUserId(rset.getString("user_id"));
		boardComment.setCoContent(rset.getString("co_content"));
		boardComment.setRegDate(rset.getDate("reg_date"));
		boardComment.setRecCount(rset.getInt("rec_count"));
		boardComment.setDetailDate(rset.getString("detail_date"));

		return boardComment;
	}

	public void deleteBoardComment(int coIdx, Connection conn) {
		
		PreparedStatement pstm = null;

		String sql = "delete from mentor_comment where co_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, coIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
	}

	public void deleteComment(int bdIdx, Connection conn) {
		
		PreparedStatement pstm = null;

		String sql = "delete from mentor_comment where bd_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		
	}

	public void getRecCount(int bdIdx, Connection conn) {
		PreparedStatement pstm = null;
		String sql = "update mentor_board set rec_count = (select rec_count+1 from mentor_board where bd_idx = ?) where bd_idx = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			pstm.setInt(2, bdIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
	}

	public List<BoardMentor> sortViewBoardList(String searchCondition, String searchKeyWord, int page,
			Connection conn) {
		List<BoardMentor> boardMentorList = new ArrayList<BoardMentor>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from ("
				+ "select rownum num, A.*"
				+ " from (select * from mentor_board where "+ searchCondition +" like ? order by reg_date desc) A"
				+ ") "
				+ "where num between ? and ? order by view_count desc";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+searchKeyWord+"%");
			pstm.setInt(2, 1+(page-1)*15);
			pstm.setInt(3,page*15);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMentorList.add(convertRowToBoardList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMentorList;
	}

	public List<BoardMentor> sortRecBoardList(String searchCondition, String searchKeyWord, int page, Connection conn) {
		
		List<BoardMentor> boardMentorList = new ArrayList<BoardMentor>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from ("
				+ "select rownum num, A.*"
				+ " from (select * from mentor_board where "+ searchCondition +" like ? order by reg_date desc) A"
				+ ") "
				+ "where num between ? and ? order by rec_count desc";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+searchKeyWord+"%");
			pstm.setInt(2, 1+(page-1)*15);
			pstm.setInt(3,page*15);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMentorList.add(convertRowToBoardList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMentorList;
	}

	public void getCommentRecCount(int coIdx, Connection conn) {
		
		PreparedStatement pstm = null;
		String sql = "update mentor_comment set rec_count = (select rec_count+1 from mentor_comment where co_idx = ?) where co_idx = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, coIdx);
			pstm.setInt(2, coIdx);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(pstm);
		}
		
	}

}
