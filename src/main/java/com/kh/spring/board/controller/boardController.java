package com.kh.spring.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.spring.board.model.dto.BoardComment;
import com.kh.spring.board.model.dto.BoardMentee;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.common.file.FileUtil;
import com.kh.spring.common.file.MultiPartParams;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentee;

/**
 * Servlet implementation class boardController
 */
@WebServlet("/board/*")
public class boardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = new BoardService();

	public boardController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");

		// System.out.println(uriArr[uriArr.length -1]);

		switch (uriArr[uriArr.length - 1]) {
		case "mentor":
			mentor(request, response);
			break;
		case "mentee":
			mentee(request, response);
			break;
		case "mentee-board-detail":
			menteeBoardDetail(request, response);
			break;
		case "mentee-board-create-form":
			menteeBoardCreateForm(request, response);
			break;
		case "mentee-upload":
			menteeBoardUpload(request, response);
			break;
		case "mentee-board-delete":
			menteeBoardDelete(request, response);
			break;
		case "mentee-board-modify":
			menteeBoardModify(request, response);
			break;
		case "mentee-board-update":
			menteeBoardUpdate(request, response);
			break;
		case "comment-upload":
			commentUpload(request, response);
			break;
		case "comment-delete":
			commentDelete(request, response);
			break;
		default:

		}
	}

	private void commentDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");
		
		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		int coIdx = Integer.parseInt(request.getParameter("coIdx"));
		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (writer.equals(client)) {
			boardService.deleteBoardComment(coIdx);
		} else {
			System.out.println("해당 댓글을 작성한 사용자가 아닙니다.");
		}

		response.sendRedirect("/board/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());

	}

	private void commentUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		Member member = (Member) request.getSession().getAttribute("authentication");
		BoardComment boardComment = new BoardComment();

		boardComment.setCoContent(request.getParameter("coComment"));
		boardComment.setUserId(member.getUserId());

		boardService.insertComment(boardComment, bdIdx);
		response.sendRedirect("/board/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());

	}

	private void menteeBoardUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileUtil util = new FileUtil();
		MultiPartParams params = util.fileUpload(request);
		BoardMentee boardMentee = new BoardMentee();

		int bdIdx = Integer.parseInt(params.getParameter("bdIdx"));

		boardService.deleteFile(bdIdx);

		boardMentee.setBdTitle(params.getParameter("bdTitle"));
		boardMentee.setBdContent(params.getParameter("bdContent"));
		List<FileDTO> fileDTOs = params.getFilesInfo();

		boardService.updateBoard(boardMentee, bdIdx, fileDTOs);

		response.sendRedirect("/board/mentee");
	}

	private void menteeBoardModify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");
		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		String client = member.getUserId();
		String writer = request.getParameter("userId");
		Map<String, Object> datas = boardService.selectBoardDetail(bdIdx);

		request.setAttribute("bdIdx", bdIdx);
		request.setAttribute("datas", datas);
		// request.setAttribute("bdTitle", datas.getBdTitle());
		// request.setAttribute("bdContent", boardMenteeDetail.getBdContent());
		if (client.equals(writer)) {
			request.getRequestDispatcher("/board/mentee-board-modify").forward(request, response);
		} else {
			response.sendRedirect("/board/mentee");
			System.out.println("해당 게시글을 작성한 사용자가 아닙니다.");
		}
	}

	private void menteeBoardDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (writer.equals(client)) {
			boardService.deleteBoard(bdIdx);
		} else {
			System.out.println("해당 게시글을 작성한 사용자가 아닙니다.");
		}

		response.sendRedirect("/board/mentee");
	}

	private void menteeBoardUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileUtil util = new FileUtil();
		MultiPartParams params = util.fileUpload(request);

		Member member = (Member) request.getSession().getAttribute("authentication");
		Mentee mentee = (Mentee) request.getSession().getAttribute("authMentee");

		BoardMentee boardMentee = new BoardMentee();

		boardMentee.setMenteeIdx(mentee.getMenteeIdx());
		boardMentee.setUserId(member.getUserId());
		boardMentee.setBdTitle(params.getParameter("bdTitle"));
		boardMentee.setBdContent(params.getParameter("bdContent"));

		List<FileDTO> fileDTOs = params.getFilesInfo();
		boardService.insertBoard(boardMentee, fileDTOs);
		response.sendRedirect("/board/mentee");
	}

	private BoardMentee registBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardMentee boardMentee = new BoardMentee();
		boardMentee.setBdTitle(request.getParameter("title"));
		boardMentee.setBdContent(request.getParameter("content"));
		return boardMentee;
	}

	private void menteeBoardCreateForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/board/mentee-board-create-form").forward(request, response);

	}

	private void menteeBoardDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (!client.equals(writer)) {
			boardService.updateViewCount(bdIdx);
		}
		Map<String, Object> datas = boardService.selectBoardDetail(bdIdx);
		request.setAttribute("datas", datas);

		List<BoardComment> boardCommentList = boardService.selectBoardComment(bdIdx);
		request.setAttribute("boardCommentList", boardCommentList);
		request.getRequestDispatcher("/board/mentee-board-detail").forward(request, response);
	}

	private void mentee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list?searchCondition=bdTitle&searchKeyWord=a
		String searchCondition_ = request.getParameter("searchCondition");
		String searchKeyWord_ = request.getParameter("searchKeyWord");
		String page_ = request.getParameter("p");

		String searchCondition = "bd_title";
		if (searchCondition_ != null && !searchCondition_.equals("")) {
			searchCondition = searchCondition_;
		}

		String searchKeyWord = "";
		if (searchKeyWord_ != null && !searchKeyWord_.equals("")) {
			searchKeyWord = searchKeyWord_;
		}

		int page = 1;
		if (page_ != null && !page_.equals("")) {
			page = Integer.parseInt(page_);
		}

		List<BoardMentee> boardMenteeList = boardService.selectBoardList(searchCondition, searchKeyWord, page);
		int count = boardService.getBoardCount(searchCondition, searchKeyWord);
		count = (int) Math.ceil(count) / 15 + 1;
		request.setAttribute("boardMenteeList", boardMenteeList);
		request.setAttribute("count", count);
		request.getRequestDispatcher("/board/mentee").forward(request, response);

	}

	private void mentor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/board/mentor").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
