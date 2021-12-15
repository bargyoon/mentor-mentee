<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<body>
	<main class="flex-shrink-0">
		<div class="site-mobile-menu site-navbar-target">
			<div class="site-mobile-menu-header">
				<div class="site-mobile-menu-close mt-3">
					<span class="icon-close2 js-menu-toggle"></span>
				</div>
			</div>
			<div class="site-mobile-menu-body"></div>
		</div>

		<div class="site-navbar-wrap">
			<div class="site-navbar site-navbar-target js-sticky-header">
				<div class="container">
					<div class="row align-items-center">
						<div class="col-4">
							<a class="navbar-brand d-none d-lg-block" href="${contextPath}/index" >MENTOR & MENTEE</a>
							<a class="navbar-brand d-lg-none ml-md-0 mr-auto" href="${contextPath}/index" >M & M</a>
						</div>
						<div class="col-8"
							style="display: flex; justify-content: flex-end;">
							<nav class="site-navigation text-right" role="navigation">
								<div class="container">
									<div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3">
										<a href="#" class="site-menu-toggle js-menu-toggle text-white"><span
											class="icon-menu h3" style="color:white"></span></a>
									</div>
									
									<ul class="site-menu main-menu js-clone-nav d-none d-lg-block">
										<c:choose>
											<c:when test="${sessionScope.authentication eq null}">
												<li><a href="${contextPath}/member/login-form" class="nav-link">로그인</a></li>
												<li><a href="${contextPath}/member/join-rule" class="nav-link">회원가입</a></li>
											</c:when>
											<c:otherwise>
												<c:if test="${authentication.role eq 'ME00'}">
													<li><a href="${contextPath}/mentoring/apply-page" class="nav-link">멘토신청</a></li>
												</c:if>
												<li><a href="${contextPath}/mentoring/manage-page" class="nav-link">멘토링 관리</a></li>
												<li>
												<c:choose>
												<c:when test="${not empty sessionScope.authMentor}">
												<a href="${contextPath}/moboard/mentor" class="nav-link">멘토게시판</a>
												</c:when>
												<c:otherwise>
												<a href="${contextPath}/meboard/mentee" class="nav-link">멘티게시판</a>
												</c:otherwise>
												</c:choose>
												
												</li>

												<li><a href="${contextPath}/todo/main" class="nav-link">일정관리</a></li>
												<li class="has-children"><a href="#" class="profile">${authentication.userName}님</a>
													<ul class="dropdown arrow-top" style="text-align: center;">
														<li><a href="${contextPath}/member/mypage" class="mypage">마이페이지</a></li>
														<li><a href="${contextPath}/member/logout" class="logout">로그아웃</a></li>
													</ul>
												</li>
											</c:otherwise>
										</c:choose>
									</ul>
								</div>
							</nav>
						</div>
					</div>
				</div>
			</div>
		</div>
		</main>
</body>
</html>