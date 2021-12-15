package com.kh.spring.board.mentor.model.service;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kh.spring.board.mentor.model.dao.BoardDao;
import com.kh.spring.board.mentor.model.dto.BoardComment;
import com.kh.spring.board.mentor.model.dto.BoardMentor;
import com.kh.spring.common.db.JDBCTemplate;
import com.kh.spring.common.exception.DataAccessException;
import com.kh.spring.common.file.FileDTO;

public class BoardService {

	private JDBCTemplate template = JDBCTemplate.getInstance();
	private BoardDao boardDao = new BoardDao();

	public List<BoardMentor> selectBoardList(String searchCondition, String searchKeyWord, int page) {

		List<BoardMentor> boardMentor = null;
		Connection conn = template.getConnection();
		try {
			boardMentor = boardDao.selectBoardList(searchCondition, searchKeyWord, page, conn);
		} finally {
			template.close(conn);
		}
		return boardMentor;
	}

	public Map<String, Object> selectBoardDetail(int bdIdx) {

		Connection conn = template.getConnection();
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			BoardMentor boardMentor = boardDao.selectBoardDetail(bdIdx, conn);
			List<FileDTO> files = boardDao.selectFileDTOs(bdIdx, conn);
			res.put("boardMentor", boardMentor);
			res.put("files", files);
		} finally {
			template.close(conn);
		}
		return res;
	}

	public void insertBoard(BoardMentor boardMentor, List<FileDTO> fileDTOs) {
		Connection conn = template.getConnection();

		try {
			boardDao.insertBoard(boardMentor, conn);

			for (FileDTO fileDTO : fileDTOs) {
				if (fileDTO.getOriginFileName() != null) {
					boardDao.insertFile(fileDTO, conn);
				}
			}

			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
	}

	public void deleteBoard(int bdIdx) {
		Connection conn = template.getConnection();

		try {

			List<FileDTO> fileDTO = boardDao.selectFileDTOs(bdIdx, conn);

			for (FileDTO fileDTO2 : fileDTO) {
				String savePath = "C:\\CODE\\upload\\";
				String subPath = fileDTO2.getSavePath();
				String fileSavePath = savePath + subPath;
				String fileName = fileDTO2.getRenameFileName();
				File deleteFile = new File(fileSavePath + fileName);
				System.out.println("지울파일이름 : " + fileSavePath + fileName);
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
			}
			boardDao.deleteComment(bdIdx, conn);
			boardDao.deleteBoard(bdIdx, conn);
			/*
			 * boardDao.updateBdIdx(bdIdx, conn); boardDao.updateScBdIdx(conn);
			 */
			boardDao.deleteBoardFileDatas(bdIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
	}

	public void updateBoard(BoardMentor boardMentor, int bdIdx, List<FileDTO> fileDTOs) {
		Connection conn = template.getConnection();

		try {
			for (FileDTO fileDTO : fileDTOs) {
				if (fileDTO.getOriginFileName() != null) {
					boardDao.insertFile(fileDTO, conn, bdIdx);
				}
			}
			boardDao.updateBoard(boardMentor, bdIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
	}

	public void deleteFile(int bdIdx) {
		Connection conn = template.getConnection();

		try {

			List<FileDTO> fileDTO = boardDao.selectFileDTOs(bdIdx, conn);

			for (FileDTO fileDTO2 : fileDTO) {
				String savePath = "C:\\CODE\\upload\\";
				String subPath = fileDTO2.getSavePath();
				String fileSavePath = savePath + subPath;
				String fileName = fileDTO2.getRenameFileName();
				File deleteFile = new File(fileSavePath + fileName);
				System.out.println("지울파일이름 : " + fileSavePath + fileName);
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
			}

			boardDao.deleteBoardFileDatas(bdIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}

	}

	public int getBoardCount(String searchCondition, String searchKeyWord) {

		Connection conn = template.getConnection();
		int count = 0;

		try {
			count = boardDao.getBoardCount(searchCondition, searchKeyWord, conn);
		} finally {
			template.close(conn);
		}
		return count;
	}

	public void updateViewCount(int bdIdx) {

		Connection conn = template.getConnection();
		try {
			boardDao.updateViewCount(bdIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}

	}

	public void insertComment(BoardComment boardComment, int bdIdx) {
		Connection conn = template.getConnection();

		try {
			boardDao.insertComment(boardComment, bdIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
	}

	public List<BoardComment> selectBoardComment(int bdIdx) {
		List<BoardComment> boardComment = null;
		Connection conn = template.getConnection();
		try {
			boardComment = boardDao.selectBoardComment(bdIdx, conn);
		} finally {
			template.close(conn);
		}
		return boardComment;
	}

	public void deleteBoardComment(int coIdx) {
		Connection conn = template.getConnection();

		try {
			boardDao.deleteBoardComment(coIdx, conn);
			template.commit(conn);
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}

	}

	public void boardRecommend(int bdIdx) {
		
		Connection conn = template.getConnection();
		try {
			boardDao.getRecCount(bdIdx, conn);
			template.commit(conn);
			
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
	}

	public List<BoardMentor> sortViewBoardList(String searchCondition, String searchKeyWord, int page) {
		
		List<BoardMentor> boardMentor = null;
		Connection conn = template.getConnection();
		try {
			boardMentor = boardDao.sortViewBoardList(searchCondition, searchKeyWord, page, conn);
		} finally {
			template.close(conn);
		}
		return boardMentor;
	}

	public List<BoardMentor> sortRecBoardList(String searchCondition, String searchKeyWord, int page) {
		
		List<BoardMentor> boardMentor = null;
		Connection conn = template.getConnection();
		try {
			boardMentor = boardDao.sortRecBoardList(searchCondition, searchKeyWord, page, conn);
		} finally {
			template.close(conn);
		}
		return boardMentor;
	}

	public void commentRecommend(int coIdx) {
		
		Connection conn = template.getConnection();
		try {
			boardDao.getCommentRecCount(coIdx, conn);
			template.commit(conn);
			
		} catch (DataAccessException e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		
	}

}
