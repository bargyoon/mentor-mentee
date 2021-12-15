package com.kh.spring.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.spring.board.model.dto.BoardComment;
import com.kh.spring.board.model.dto.BoardMentee;
import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;

public class BoardDao {

	private JDBCTemplate template = JDBCTemplate.getInstance();

	public List<BoardMentee> selectBoardList(String searchCondition, String searchKeyWord, int page, Connection conn) {
		List<BoardMentee> boardMenteeList = new ArrayList<BoardMentee>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from ("
				+ "select rownum num, A.*"
				+ " from (select * from mentee_board where "+ searchCondition +" like ? order by reg_date desc) A"
				+ ") "
				+ "where num between ? and ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%"+searchKeyWord+"%");
			pstm.setInt(2, 1+(page-1)*15);
			pstm.setInt(3,page*15);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMenteeList.add(convertRowToBoardList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMenteeList;
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
				+ "from (select * from mentee_board where "+ searchCondition +" like ? order by reg_date desc) A"
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
	
	public BoardMentee getNextBoard(int bdIdx) {
		
		String sql = "select * from mentee_board "
				+ "where bd_idx = ("
				+ "select bd_idx from mentee_board "
				+ "where reg_date > (select reg_date from mentee_board where bd_idx=3) "
				+ "and rownum = 1"
				+ ")";
		
		return null;
	}
	
	public BoardMentee getPrevBoard(int bdIdx) {
		
		String sql = "select bd_idx from ("
				+ "select * from mentee_board order by reg_date desc) "
				+ "where reg_date < (select reg_date from mentee_board where bd_idx = 3 "
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

	public BoardMentee selectBoardDetail(int bdIdx, Connection conn) {
		BoardMentee boardMenteeDetail = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select bd_idx,bd_title,reg_date,view_count,rec_count,bd_content,mentee_idx,user_id "
				+ "from mentee_board where bd_idx=?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMenteeDetail = convertRowToBoardList(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMenteeDetail;
	}

	public void insertBoard(BoardMentee boardMentee, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "insert into mentee_board(bd_idx,bd_title,reg_date,view_count,rec_count,bd_content,mentee_idx,user_id)"
				+ "values(sc_mentee_bd_idx.nextval,?,sysdate,0,0,?,?,?)";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, boardMentee.getBdTitle());
			pstm.setString(2, boardMentee.getBdContent());
			pstm.setInt(3, boardMentee.getMenteeIdx());
			pstm.setString(4, boardMentee.getUserId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
	}

	private BoardMentee convertRowToBoardList(ResultSet rset) throws SQLException {

		BoardMentee boardMentee = new BoardMentee();
		boardMentee.setBdIdx(rset.getInt("bd_idx"));
		boardMentee.setBdTitle(rset.getString("bd_title"));
		boardMentee.setRegDate(rset.getDate("reg_date"));
		boardMentee.setViewCount(rset.getInt("view_count"));
		boardMentee.setRecCount(rset.getInt("rec_count"));
		boardMentee.setBdContent(rset.getString("bd_content"));
		boardMentee.setMenteeIdx(rset.getInt("mentee_idx"));
		boardMentee.setUserId(rset.getString("user_id"));

		return boardMentee;
	}

	public void deleteBoard(int bdIdx, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "delete from mentee_board where bd_idx = ?";

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

	public void updateBoard(BoardMentee boardMentee, int bdIdx, Connection conn) {

		PreparedStatement pstm = null;

		String sql = "update mentee_board set bd_title = ?, bd_content = ?, reg_date = sysdate where bd_idx = ?";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, boardMentee.getBdTitle());
			pstm.setString(2, boardMentee.getBdContent());
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
				+ "values(sc_file_idx.nextval,sc_mentee_bd_idx.currval,?,?,?)";

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
		String sql = "update mentee_board set view_count = (select view_count+1 from mentee_board where bd_idx = ?) where bd_idx = ?";
		
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

		String sql = "insert into mentee_comment(co_idx,user_id,co_content,reg_date,rec_count,bd_idx)"
				+ "values(sc_mentee_co_idx.nextval,?,?,sysdate,0,?)";

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
		
		List<BoardComment> boardMenteeCommentList = new ArrayList<BoardComment>();
		PreparedStatement pstm = null;
		ResultSet rset = null;

		String sql = "select * from mentee_comment where bd_idx = ? order by co_idx desc";

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bdIdx);
			rset = pstm.executeQuery();
			while (rset.next()) {
				boardMenteeCommentList.add(convertRowToBoardCommentList(rset));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return boardMenteeCommentList;
	}

	private BoardComment convertRowToBoardCommentList(ResultSet rset) throws SQLException {
		
		BoardComment boardComment = new BoardComment();
		boardComment.setCoIdx(rset.getInt("co_idx"));
		boardComment.setUserId(rset.getString("user_id"));
		boardComment.setCoContent(rset.getString("co_content"));
		boardComment.setRegDate(rset.getDate("reg_date"));
		boardComment.setRecCount(rset.getInt("rec_count"));

		return boardComment;
	}

	public void deleteBoardComment(int coIdx, Connection conn) {
		
		PreparedStatement pstm = null;

		String sql = "delete from mentee_comment where co_idx = ?";

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

}
