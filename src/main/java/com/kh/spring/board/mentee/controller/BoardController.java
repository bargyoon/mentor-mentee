package com.kh.spring.board.mentee.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.spring.board.mentee.model.dto.BoardComment;
import com.kh.spring.board.mentee.model.dto.BoardMentee;
import com.kh.spring.board.mentee.model.service.BoardService;
import com.kh.spring.common.file.FileDTO;
import com.kh.spring.common.file.FileUtil;
import com.kh.spring.common.file.MultiPartParams;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentee;

/**
 * Servlet implementation class boardController
 */
@WebServlet("/meboard/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BoardService boardService = new BoardService();

	public BoardController() {
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
		case "comment-recommend":
			commentRecommend(request,response);
			break;
		case "content-recommend":
			contentRecommend(request, response);
			break;
		default:

		}
	}

	private void commentRecommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");
		
		int coIdx = Integer.parseInt(request.getParameter("coIdx"));
		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));

		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (!writer.equals(client)) {
			boardService.commentRecommend(coIdx);
		} else {
			request.setAttribute("msg", "답글 작성자는 추천할 수 없습니다.");
			request.setAttribute("url", "/meboard/mentee");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

		response.sendRedirect("/meboard/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());
	}

	private void contentRecommend(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));

		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (!writer.equals(client)) {
			boardService.boardRecommend(bdIdx);
		} else {
			request.setAttribute("msg", "게시글 작성자는 추천할 수 없습니다.");
			request.setAttribute("url", "/meboard/mentee");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

		response.sendRedirect("/meboard/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());
	}

	private void commentDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		int coIdx = Integer.parseInt(request.getParameter("coIdx"));
		String client = member.getUserId();
		String writer = request.getParameter("userId");

		if (writer.equals(client)) {
			boardService.deleteBoardComment(coIdx);
		} else {
			System.out.println("해당 댓글을 작성한 사용자가 아닙니다.");
			request.setAttribute("msg", "해당 댓글을 작성한 사용자가 아닙니다.");
			request.setAttribute("url", "/meboard/mentee");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

		response.sendRedirect("/meboard/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());

	}

	private void commentUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		Member member = (Member) request.getSession().getAttribute("authentication");
		BoardComment boardComment = new BoardComment();

		boardComment.setCoContent(request.getParameter("coComment"));
		boardComment.setUserId(member.getUserId());

		boardService.insertComment(boardComment, bdIdx);
		response.sendRedirect("/meboard/mentee/mentee-board-detail?bdIdx=" + bdIdx + "&userId=" + member.getUserId());

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

		response.sendRedirect("/meboard/mentee");
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
		System.out.println(client);
		System.out.println(writer);
		// request.setAttribute("bdTitle", datas.getBdTitle());
		// request.setAttribute("bdContent", boardMenteeDetail.getBdContent());
		if (client.equals(writer)) {
			request.getRequestDispatcher("/board/mentee-board-modify").forward(request, response);
		} else {
			request.setAttribute("msg", "해당 게시글을 작성한 사용자가 아닙니다.");
			request.setAttribute("url", "/meboard/mentee");
			request.getRequestDispatcher("/common/result").forward(request, response);
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
			request.setAttribute("msg", "해당 게시글을 작성한 사용자가 아닙니다.");
			request.setAttribute("url", "/meboard/mentee");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

		response.sendRedirect("/meboard/mentee");
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
		response.sendRedirect("/meboard/mentee");
	}

	private void menteeBoardCreateForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/board/mentee-board-create-form").forward(request, response);

	}

	private void menteeBoardDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member member = (Member) request.getSession().getAttribute("authentication");

		int bdIdx = Integer.parseInt(request.getParameter("bdIdx"));
		
		if(member==null) {
			request.setAttribute("msg", "해당 경로는 잘못된 접근입니다.");
			request.setAttribute("url", "/");
			request.getRequestDispatcher("/common/result").forward(request, response);
			return;
		}
		
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
		if (request.getParameter("sort") == null) {
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

			List<BoardMentee> boardMenteeList = null;

			int count = boardService.getBoardCount(searchCondition, searchKeyWord);
			count = (int) Math.ceil(count) / 15 + 1;
			boardMenteeList = boardService.selectBoardList(searchCondition, searchKeyWord, page);
			request.setAttribute("boardMenteeList", boardMenteeList);
			request.setAttribute("count", count);
			request.getRequestDispatcher("/board/mentee").forward(request, response);
		} else {
			String sortView = request.getParameter("sort");

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

			List<BoardMentee> boardMenteeList = null;

			if (sortView.equals("view")) {
				boardMenteeList = boardService.sortViewBoardList(searchCondition, searchKeyWord, page);
			} else if (sortView.equals("recommend")) {
				boardMenteeList = boardService.sortRecBoardList(searchCondition, searchKeyWord, page);
			} else {
				boardMenteeList = boardService.selectBoardList(searchCondition, searchKeyWord, page);
			}
			int count = boardService.getBoardCount(searchCondition, searchKeyWord);
			count = (int) Math.ceil(count) / 15 + 1;
			request.setAttribute("boardMenteeList", boardMenteeList);
			request.setAttribute("count", count);
			request.getRequestDispatcher("/board/mentee").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
