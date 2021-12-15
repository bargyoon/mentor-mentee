<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>TODO-DETAIL</title>

<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/todo/reset.css">
<link rel="stylesheet" href="/resources/css/todo/test.css">


<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.9.0/main.min.css">
<script src='https://cdn.jsdelivr.net/combine/npm/fullcalendar@5.9.0/main.min.js,
											npm/fullcalendar@5.9.0/locales-all.min.js'></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>

<script
  src="https://code.jquery.com/jquery-3.6.0.js"
  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  crossorigin="anonymous"></script>
  
</head>


<body class="d-flex flex-column h-100">
    
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
 
<main class="flex-shrink-0">
            
<script src="/resources/js/todo/todo-detail.js"></script>

<input type="hidden" name="color" id="todo_color" />
<input type="hidden" name="start" id="todo_start" />
<input type="hidden" name="end" id="todo_end" />
<input type="hidden" name="title" id="todo_title" />
<input type="hidden" name="todoIdx" id="todo_idx" />

<div class="wrap-calendar"><div id='calendar' class="calendar"></div></div>


	<!-- Footer-->

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<%@ include file="/WEB-INF/views/include/jsFiles.jsp" %>
</body>
</html>