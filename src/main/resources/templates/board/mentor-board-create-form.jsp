<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" href="/resources/css/board/board-create.css">
</head>
<body class="d-flex flex-column h-100">

	<%@ include file="/WEB-INF/views/include/nav.jsp"%>

	<!-- Header-->
	<div class="create_section">
		<div id="contentHeader">
			<div id="contentTitle">
				<p>멘토 게시판</p>
			</div>
		</div>
		<hr>
		<form action="/moboard/mentor/mentor-upload" enctype="multipart/form-data" method="post">
		<div id="createBody">
			<div id=createHeader>
			<p>제목 : </p><input type="text" name="bdTitle" id="createTitle"/>
			<input type="file" id="fileBtn" name="files" value="파일" multiple/>
			</div>
			<hr>
			<div id="createBox">
				<textarea id="createBox" name="bdContent" required="required" maxlength="1000"></textarea>
			</div>
			<hr>
		</div>

		<div class="create_footer">
			<button id="returnButton" type="button" onclick="location.href='mentor'">목록</button>
			<div id="wrapButton">
				<button type="submit">등록</button>
			</div>
		</div>
		</form>
	</div>

	<!-- Features section-->


	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<%@ include file="/WEB-INF/views/include/jsFiles.jsp"%>


</body>
</html>