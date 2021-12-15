<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>MENTOR & MENTEE</title>

<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body class="d-flex flex-column h-100">

	<%@ include file="/WEB-INF/views/include/nav.jsp"%>


	<!-- Header-->
	<header class="py-5" style="background-color: #343a40">

		<div class="container px-5" style="margin-top: 5%">
			<div class="row gx-5 align-items-center justify-content-center">
				<div class="col-lg-8 col-xl-7 col-xxl-6">
					<div class="my-5 text-center text-xl-start">
						<h1 class="display-5 fw-bolder text-white mb-3">
							선배가 직접 들려주는 <br>자신의 이야기
						</h1>
						<p class="lead fw-bolder mb-5"
							style="border: solid 2px gray; color: white; padding-top: 2%; padding-bottom: 2%">
							자신에게 맞는 선배를 찾아 멘토링을 받아보세요<br> 전보다 <span
								class="shadow-dark-1" style="color: orange;">더 빠르게 높은 곳으로</span>
							갈수 있을 거예요
						</p>
						<div class="d-grid gap-3 d-sm-flex justify-content-sm-center">
							<a class="btn btn-primary btn-lg px-4 me-sm-3 fw-bolder"
								href="/mentoring/apply-page" style="box-shadow: 4px 3px 3px black">멘토링
								신청하기</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!-- Features section-->
	<section class="py-3" id="features">
		<div class="container px-5 my-5 ">
			<div class="row gx-5">
				<div class="col-lg-5 mb-5 mb-lg-5">
					<h5 class="fw-bolder mb-0" style="text-decoration: underline;">Mentor-Mentee
						연결</h5>
					<h3 class="fw-bolder mb-0 shadow-gray-2" style="margin-top: 0;">
						<span style="background-color: orange; color: white;">M&M</span>은
						사용자의 조건에 맞는<br>Mentor를 연결해줍니다.
					</h3>
					<h6 class="fw-bolder mb-0" style="margin-top: 2%; color: gray;">
						학교, 학과, 원하는 조언 등<br>다양한 조건을 선택해 멘토를 선택할 수 있어요.
					</h6>
				</div>
			</div>

			<div class="ms-5">
				<img src="/resources/img/main2.jpg" class="img-fluid img-main">



			</div>
		</div>
	</section>

	<section class="py-5" id="features" style="background-color: #343a40">
		<div class="container px-5 my-5 ">
			<div class="row gx-5">
				<div class="col-lg-5 mb-5 mb-lg-5">
					<h5 class="fw-bolder mb-0"
						style="text-decoration: underline; color: white;">Check
						Detail</h5>
					<h3 class="fw-bolder mb-0 shadow-dark-2"
						style="margin-top: 0; color: white;">
						마음에 드는 멘토를 <span style="background-color: orange;">클릭</span>해<br>
						멘토의 <span style="background-color: orange;">상세이력</span>을 보고 결정하세요
						!
					</h3>
					<h6 class="fw-bolder mb-0"
						style="margin-top: 2%; color: rgb(224, 224, 224);">
						간소화된 멘토정보를 클릭하면<br>멘토의 상세이력을 확인 할 수 있습니다.
					</h6>
				</div>
			</div>


			<div class="ms-5">
				<img src="/resources/img/main3.jpg" class="img-fluid img-main">

			</div>
		</div>
	</section>


	
	<!-- Footer-->

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<%@ include file="/WEB-INF/views/include/jsFiles.jsp"%>
</body>
</html>