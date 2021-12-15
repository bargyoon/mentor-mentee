<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MENTOR & MENTEE - 멘토링 추가 정보 입력</title>

<%@ include file="/WEB-INF/views/include/head.jsp" %>
</head>
<body class="d-flex flex-column h-75">
	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<c:set var="menteeIdx" value="${requestScope.menteeIdx}"/>
	<main class="flex-shrink-0">
		<section class="py-5 mt-4">
	        <div class="container px-5 mt-5" style="margin-top: 0;">
	            <div class="row gx-5 px-5 mx-5">
	                <div class="mb-5 px-5">
	                    <div class="card h-100 shadow border-0">
	                        <div class="card-body p-4">
	                            <h5 class="card-title mb-1 text-decoration-none mt-2">추가 입력 사항</h5>
	                            <p class="small card-text mb-0">멘토링 진행을 위해 입력해야하는 항목입니다. (* 가격 제외)</p>
	                            <hr>
	                            <form action="/mentoring/regist-mentoring" class="mt-2">
	                            	<div class="badge bg-dark bg-gradient rounded-pill mb-2" style="width: 100%">추가 입력 항목</div>
	                            	<input type="hidden" name="mentee_idx" value="${menteeIdx}">
	                            	<div class="card-group justify-content-around flex-column align-items-center px-5 my-1">
		                            	<p class="small mb-2">1. 희망 시작 일자  <input id="start-date" class="m-2 px-2 rounded-pill" type="date" name="start_date" required></p>
	                            		<p class="small mb-2">2. 희망 종료 일자 <input id="end-date" class="m-2 px-2 rounded-pill" type="date" name="end_date" required></p>
	                            		<p class="small mb-2">3. 진행 가격 <input class="m-2 rounded-pill" type="number" name="price" ></p>
	                            	</div>
				                    <div class="d-flex mt-3" style="justify-content: center;">
										<button class="btn btn-primary-dark btn-lg px-4 ms-sm-3 fw-bolder" id="submit-btn">작성 완료</button>
				                    </div>
								</form>
		                    </div>
						</div>
	                </div>
	            </div>
	        </div>
        </section>
	</main>

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>		
	<script src="/resources/js/mentoring/accept-page.js"></script>
	<%@ include file="/WEB-INF/views/include/jsFiles.jsp" %>

</body>
</html>