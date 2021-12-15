package com.kh.spring.member.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.spring.common.file.FileDTO;
import com.kh.spring.common.file.FileUtil;
import com.kh.spring.common.file.MultiPartParams;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.dto.Mentee;
import com.kh.spring.member.model.dto.Mentor;
import com.kh.spring.member.model.service.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
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

		switch (uriArr[uriArr.length - 1]) {
		case "login-form":
			loginForm(request, response);
			break;
		case "login":
			login(request, response);
			break;
		case "kakao-login":
			kakaoLogin(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		case "forget-password":
			forgetPassword(request, response);
			break;

		case "join-form-mentor":
			joinFormMentor(request, response);

			break;
		case "join-form-mentee":
			joinFormMentee(request, response);
			break;
		case "join-mentee":
			joinMentee(request, response);
			break;
		case "join-mentor":
			joinMentor(request, response);
			break;
		case "join-impl":
			joinImpl(request, response);
			break;
		case "password-impl":
			passwordImpl(request, response);
			break;
		case "password-impla":
			passwordImpl(request, response);
			break;
		case "delete-member":
			deleteMember(request, response);
			break;
		case "join-rule":
			joinRule(request, response);
			break;
		case "id-check":
			checkID(request, response);
			break;
		case "uploadImg":
			uploadImg(request, response);
			break;
		case "modify-mentor":
			modifyMentor(request, response);
			break;
		case "modify-mentee":
			modifyMentee(request, response);
			break;
		case "modify-password":
			modifyPassword(request, response);
			break;
		case "modify-account":
			modifyAccount(request, response);
			break;
		case "confirm-pw":
			confirmPassword(request, response);
			break;
		case "mypage":
			mypage(request, response);
			break;
		case "mentor-info":
			mentorInfoForm(request, response);

			break;
		case "test":
			test(request, response);
			break;
		case "kakao-auth":
			kakaoAuth(request, response);

			break;
		case "reset-password":
			resetPassword(request, response);

			break;
		case "change-password":
			changePassword(request, response);
			
			break;

		default:

		}

	}

	

	private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("persistUser");
		String password = request.getParameter("resetNewPw");
		member.setPassword(password);
		if (memberService.modifyMember(member) != 0) {
			request.setAttribute("msg", "비밀번호 수정이 완료되었습니다.");
			request.setAttribute("url", "/member/login-form");
			request.getRequestDispatcher("/common/result").forward(request, response);
			
			session.removeAttribute("persistUser");
			session.removeAttribute("persist-token");
			response.sendRedirect("/member/login-form");

		}

		
	}

	private void passwordImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/member/change-pw").forward(request, response);
		
	}

	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("logemail");
		
		Member member = null;
		member = memberService.selectMemberByEmail(email);

		if (member == null) {
			response.sendRedirect("/member/forget-password?err=1");
			return;
		}else {
			String persistToken = UUID.randomUUID().toString();
			request.getSession().setAttribute("persistUser", member);
			request.getSession().setAttribute("persist-token", persistToken);

			memberService.resetEmail(member, persistToken);

			request.setAttribute("msg", "비밀번호 초기화를 위한 이메일이 발송되었습니다.");
			request.setAttribute("url", "/member/login-form");
			request.getRequestDispatcher("/common/result").forward(request, response);

		}
		
	}

	private void kakaoAuth(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String kakaoId = request.getParameter("kakao");
		if (memberService.selectMemberByKakaoId(kakaoId) != 0) {
			response.getWriter().print("disable");
		} else {
			Member member = (Member) request.getSession().getAttribute("authentication");
			member.setKakaoJoin("y");
			response.getWriter().print("available");
			if (memberService.kakaoAuth(member, kakaoId) != 0) {

			}
		}

	}

	private void kakaoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userIdx = Integer.parseInt(request.getParameter("userIdx"));
		Member member = null;
		Mentor mentor = null;
		Mentee mentee = null;
		member = memberService.selectMemberByIdx(userIdx);

		if (member == null) {
			response.sendRedirect("/member/login-form?err=1");
			return;
		}
		if (member.getRole().startsWith("ME")) {
			mentee = memberService.selectMenteeByRole(member);
			request.getSession().setAttribute("authMentee", mentee);
		} else {
			mentor = memberService.selectMentorByRole(member);
			request.getSession().setAttribute("authMentor", mentor);
		}
		request.getSession().setAttribute("authentication", member);
		request.getSession().removeAttribute("kakao");
		request.getSession().removeAttribute("kakaoId");
		response.sendRedirect("/");

	}

	private void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String kakaoId = request.getParameter("kakao");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String name = request.getParameter("name");
		Member member = new Member();
		member.setEmail(email);
		member.setUserName(name);
		member.setGender(gender);

		int userIdx = memberService.selectMemberByKakaoId(kakaoId);

		if (userIdx == 0) {
			response.getWriter().print("notMember");
			request.getSession().setAttribute("kakao", member);
			request.getSession().setAttribute("kakaoId", kakaoId);

		} else {
			response.getWriter().print(userIdx);
		}

	}

	private void deleteMember(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Member member = (Member) request.getSession().getAttribute("authentication");

		String userId = member.getUserId();
		String password = request.getParameter("password");

		member = memberService.memberAuthenticate(userId, password);

		if (member == null) {
			response.sendRedirect("/member/confirm-pw?err=1");
			return;
		}
		member.setIsLeave(1);
		if (memberService.deleteMember(member) != 0 && memberService.setIsLeave(member) != 0) {

			request.getSession().removeAttribute("authentication");
			request.getSession().removeAttribute("authMentor");
			request.getSession().removeAttribute("authMentee");
			request.getSession().removeAttribute("files");

			request.setAttribute("msg", "회원탈퇴가 완료되었습니다. 감사합니다");
			request.setAttribute("url", "/");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}
	}

	private void modifyPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newPassword = request.getParameter("newPw");
		Member member = (Member) request.getSession().getAttribute("authentication");
		member.setPassword(newPassword);
		if (memberService.modifyMember(member) != 0) {
			request.setAttribute("msg", "비밀번호 수정이 완료되었습니다.");
			request.setAttribute("url", "/member/mypage");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

	}

	private void modifyMentee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String schoolName = request.getParameter("schoolName");
		String major = request.getParameter("major");
		int grade = Integer.parseInt(request.getParameter("grade"));
		String hopeUniversity = request.getParameter("hopeUniversity");
		String hopeMajor = request.getParameter("hopeMajor");
		Member member = (Member) request.getSession().getAttribute("authentication");
		Mentee mentee = (Mentee) request.getSession().getAttribute("authMentee");

		member.setUserName(userName);
		member.setEmail(email);
		member.setAddress(address);
		member.setPhone(phone);

		mentee.setSchoolName(schoolName);
		mentee.setMajor(major);
		mentee.setGrade(grade);
		mentee.setHopeUniversity(hopeUniversity);
		mentee.setHopeMajor(hopeMajor);

		if (memberService.modifyMentee(member, mentee) != 0) {
			request.setAttribute("msg", "회원정보 수정이 완료되었습니다.");
			request.setAttribute("url", "/member/mypage");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}
	}

	private void modifyMentor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String universityName = request.getParameter("university");
		String grade = request.getParameter("grade");
		String major = request.getParameter("major");
		String wantDay = request.getParameter("wantDay");
		String wantTime = request.getParameter("wantTime");
		String requirement = request.getParameter("requirement");
		String[] historyArr = request.getParameterValues("history");
		Member member = (Member) request.getSession().getAttribute("authentication");
		Mentor mentor = (Mentor) request.getSession().getAttribute("authMentor");
		String history = mentor.getHistory();


		member.setUserName(userName);
		member.setEmail(email);
		member.setAddress(address);
		member.setPhone(phone);

		mentor.setUniversityName(universityName);
		mentor.setGrade(Integer.parseInt(grade));
		mentor.setMajor(major);
		mentor.setWantDay(wantDay);
		mentor.setWantTime(wantTime);
		mentor.setRequirement(requirement);
		if(history != null) {
			if (historyArr != null) {

				for (int i = 0; i < historyArr.length; i++) {

					history += ("," + historyArr[i]);

				}
				mentor.setHistory(history);
			}
		}else {
			
			if (historyArr != null) {

				for (int i = 0; i < historyArr.length; i++) {
					if(i == 0) {
						history = historyArr[i];
					}else {
						history += ("," + historyArr[i]);
					}
					

				}
				mentor.setHistory(history);
			}
		}
		
		

		if (memberService.modifyMentor(member, mentor) != 0) {
			request.setAttribute("msg", "회원정보 수정이 완료되었습니다.");
			request.setAttribute("url", "/member/mypage");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}

	}

	private void modifyAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountNum = request.getParameter("accountNum");
		String bank = request.getParameter("bankName");
		Member member = (Member) request.getSession().getAttribute("authentication");
		Mentor mentor = (Mentor) request.getSession().getAttribute("authMentor");
		mentor.setAccountNum(accountNum);
		mentor.setBank(bank);
		
		if (memberService.modifyMentor(member, mentor) != 0) {
			request.setAttribute("msg", "계좌 정보 수정이 완료되었습니다.");
			request.setAttribute("url", "/member/mypage");
			request.getRequestDispatcher("/common/result").forward(request, response);
		}
	}
	
	private void joinRule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/member/join-rule").forward(request, response);

	}

	private void mentorInfoForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userIdx = Integer.parseInt(request.getParameter("user_idx"));
		Member member = null;
		Mentor mentor = null;
		member = memberService.selectMemberByIdx(userIdx);
		
		mentor = memberService.selectMentorByRole(member);
		FileDTO file = memberService.selectBoardDetail(mentor.getMentorIdx());
		request.getSession().setAttribute("files", file);
		request.getSession().setAttribute("mentorInfo", mentor);
		request.getSession().setAttribute("userInfo", member);

		request.getRequestDispatcher("/member/mentor-info").forward(request, response);

	}

	private void forgetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/member/forget-password").forward(request, response);
	}

	private void confirmPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/member/confirm-pw").forward(request, response);

	}

	private void mypage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("authMentor") != null) {
			Mentor mentor = (Mentor) request.getSession().getAttribute("authMentor");
			int mentorIdx = mentor.getMentorIdx();
			FileDTO file = memberService.selectBoardDetail(mentorIdx);
			request.getSession().setAttribute("files", file);

		}

		request.getRequestDispatcher("/member/mypage").forward(request, response);

	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("authentication");
		request.getSession().removeAttribute("authMentor");
		request.getSession().removeAttribute("authMentee");
		request.getSession().removeAttribute("files");
		request.getSession().removeAttribute("kakao");
		request.getSession().removeAttribute("kakaoId");
		response.sendRedirect("/index");
	}

	private void checkID(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");

		Member member = memberService.selectMemberById(userId);

		if (member == null) {
			response.getWriter().print("available");
		} else {
			response.getWriter().print("disable");
		}

	}

	private void uploadImg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileUtil util = new FileUtil();
		MultiPartParams params = util.fileUpload(request);

		Mentor mentor = (Mentor) request.getSession().getAttribute("authMentor");
		List<FileDTO> fileDTOs = params.getFilesInfo();

		memberService.insertImg(mentor, fileDTOs);

		response.sendRedirect("/member/mypage");
	}

	private void joinMentee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String schoolName = request.getParameter("schoolName");
		String major = request.getParameter("major");
		String grade = request.getParameter("grade");
		String hopeUniversity = request.getParameter("hopeUniversity");
		String hopeMajor = request.getParameter("hopeMajor");

		Member member = new Member();
		Mentee mentee = new Mentee();

		member = commonMember(request, response, member);
		member.setRole("ME00");
		mentee.setSchoolName(schoolName);
		mentee.setMajor(major);
		mentee.setGrade(Integer.parseInt(grade));
		mentee.setHopeUniversity(hopeUniversity);
		mentee.setHopeMajor(hopeMajor);

		String persistToken = UUID.randomUUID().toString();
		request.getSession().setAttribute("persistUser", member);
		request.getSession().setAttribute("persistMentee", mentee);
		request.getSession().setAttribute("persist-token", persistToken);

		memberService.authenticateEmail(member, persistToken);

		request.setAttribute("msg", "회원가입을 위한 이메일이 발송되었습니다.");
		request.setAttribute("url", "/member/login-form");
		request.getRequestDispatcher("/common/result").forward(request, response);

		/*
		 * if (member.getKakaoJoin().equals("y")) { if
		 * (memberService.insertMentee(member, mentee, kakaoId) != 0) {
		 * System.out.println("카카오회원가입"); }
		 * 
		 * } else { if (memberService.insertMentee(member, mentee) != 0) {
		 * System.out.println("일반회원가입"); } }
		 */

	}

	private void joinMentor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String universityName = request.getParameter("universityName");
		String universityType = request.getParameter("universityType");
		String grade = request.getParameter("grade");
		String major = request.getParameter("major");
		String wantDay = request.getParameter("wantDay");
		String wantTime = request.getParameter("wantTime");
		String requirement = request.getParameter("requirement");
		String[] historyArr = request.getParameterValues("history");
		String accountNum = request.getParameter("accountNum");
		String bank = request.getParameter("bankName");
		
		Member member = new Member();
		Mentor mentor = new Mentor();

		member = commonMember(request, response, member);
		member.setRole("MO00");
		mentor.setUniversityName(universityName);
		mentor.setUniversityType(universityType);
		mentor.setGrade(Integer.parseInt(grade));
		mentor.setMajor(major);
		mentor.setWantDay(wantDay);
		mentor.setWantTime(wantTime);
		mentor.setRequirement(requirement);
		if (historyArr.length != 0) {
			String history = "";

			for (int i = 0; i < historyArr.length; i++) {
				if (i == 0) {
					history = historyArr[i];
				} else {
					history += ("," + historyArr[i]);
				}

			}
			mentor.setHistory(history);
		}
		mentor.setAccountNum(accountNum);
		mentor.setBank(bank);

		String persistToken = UUID.randomUUID().toString();
		request.getSession().setAttribute("persistUser", member);
		request.getSession().setAttribute("persistMentor", mentor);
		request.getSession().setAttribute("persist-token", persistToken);

		memberService.authenticateEmail(member, persistToken);

		request.setAttribute("msg", "회원가입을 위한 이메일이 발송되었습니다.");
		request.setAttribute("url", "/member/login-form");
		request.getRequestDispatcher("/common/result").forward(request, response);

		/*
		 * if(member.getKakaoJoin().equals("y")) { if
		 * (memberService.insertMentor(member, mentor, kakaoId) != 0) {
		 * System.out.println("카카오회원가입"); }
		 * 
		 * 
		 * }else { if (memberService.insertMentor(member, mentor) != 0) {
		 * System.out.println("일반회원가입"); } }
		 */

	}

	private void joinImpl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String kakaoId = (String) request.getSession().getAttribute("kakaoId");
		Member member = (Member) session.getAttribute("persistUser");
		Mentor mentor = (Mentor) session.getAttribute("persistMentor");
		Mentee mentee = (Mentee) session.getAttribute("persistMentee");

		if (member.getKakaoJoin().equals("y")) {

			if (mentor != null) {
				if (memberService.insertMentor(member, mentor, kakaoId) != 0) {
					System.out.println("카카오회원가입");
				}
			} else {
				if (memberService.insertMentee(member, mentee, kakaoId) != 0) {
					System.out.println("카카오회원가입");
				}
			}

		} else {
			if (mentor != null) {
				if (memberService.insertMentor(member, mentor) != 0) {
					System.out.println("일반회원가입");
				}
			} else {
				if (memberService.insertMentee(member, mentee) != 0) {
					System.out.println("일반회원가입");
				}
			}

		}

		// 같은 persistUser값이 두 번 DB에 입력되지 않도록 사용자 정보와 인증을 만료시킴
		session.removeAttribute("persistUser");
		session.removeAttribute("persistMentor");
		session.removeAttribute("persistMentee");
		session.removeAttribute("persist-token");
		response.sendRedirect("/member/login-form");

	}

	private void joinFormMentor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/member/join-form-mentor").forward(request, response);
	}

	private void joinFormMentee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/member/join-form-mentee").forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * String userId = request.getParameter("userId"); String password =
		 * request.getParameter("password");
		 * 
		 * Member member = null;
		 * 
		 * // 1. 시스템에서 문제가 생겨서(DB가 뻗었다던데... 외부 API서버가 죽었다던가...) // 예외가 발생하는 경우. => 예외처리를
		 * service 단에서 처리 member = memberService.memberAuthenticate(userId, password);
		 * 
		 * 
		 * request.setAttribute("msg", "회원정보를 조회하는 도중 예외가 발생했습니다.");
		 * request.setAttribute("url", "/index");
		 * request.getRequestDispatcher("/error/result").forward(request, response);
		 * return;
		 * 
		 * 
		 * // 2. 사용자가 잘못된 아이디나 비밀번호를 입력한 경우. // 사용자에게 아이디나 비밀번호가 틀렸음을 알림, login-form 으로
		 * redirect
		 * 
		 * if (member == null) { response.sendRedirect("/member/login-form?err=1");
		 * return; }
		 */
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		Member member = null;
		Mentor mentor = null;
		Mentee mentee = null;
		member = memberService.memberAuthenticate(userId, password);

		if (member == null) {
			response.sendRedirect("/member/login-form?err=1");
			return;
		}
		if (member.getRole().startsWith("ME")) {
			mentee = memberService.selectMenteeByRole(member);
			request.getSession().setAttribute("authMentee", mentee);
		} else {
			mentor = memberService.selectMentorByRole(member);
			request.getSession().setAttribute("authMentor", mentor);
		}
		request.getSession().setAttribute("authentication", member);

		response.sendRedirect("/");

	}

	private void loginForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("kakao");
		request.getRequestDispatcher("/member/login").forward(request, response);
	}

	private Member commonMember(HttpServletRequest request, HttpServletResponse response, Member member)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender-radio");
		String address = request.getParameter("address");
		String phone = request.getParameter("countryCode") + request.getParameter("phone");
		String nickname = request.getParameter("nickname");
		String kakaoLog = (String) request.getSession().getAttribute("kakaoId");
		member.setUserName(userName);
		member.setUserId(userId);
		member.setPassword(password);
		member.setEmail(email);
		if (gender.equals("male")) {
			member.setGender("m");
		} else {
			member.setGender("f");
		}
		member.setAddress(address);
		member.setPhone(phone);
		member.setNickname(nickname);
		if (kakaoLog != null) {
			member.setKakaoJoin("y");
		}else {
			member.setKakaoJoin("n");
		}
		return member;
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
