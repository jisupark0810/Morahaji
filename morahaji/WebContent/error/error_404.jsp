<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page isErrorPage="true"%>
<%
response.setStatus(HttpServletResponse.SC_OK);
%>

<!DOCTYPE html>
<html>
<head>
<!-- �ĺ��� -->
<link rel="shortcut icon" href="img/pavicon.ico">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<!-- �ĺ��� -->
<style>
#errorPage {
	text-align: center;
	width: 100%;
	height: 80%;
	margin-bottom: 50px;
	min-height: 800px;
}

#errorImg {
	height: 70%;
	width: 100%;
}

.historyBack {
	text-decoration: none;
	width: 100%;
	background: none;
	border: none;
}
</style>
<script>
</script>
</head>
<body>
	<!-- Header Start -->
	<nav>
		<jsp:include page="../index/header.jsp" flush="false" /><br> <br>
	</nav>
	<!-- Header End -->

	<!-- Body Start -->
	<div id=errorPage>
		<button class=historyBack>���ư���</button>
		<br> <a href="main.index"> <img id=errorImg
			src="img/404error.gif" title="���� �������� �̵�">
		</a>
	</div>
	<!-- Body End -->

	<!-- Footer Start -->
	<footer>
		<jsp:include page="../index/bottom.jsp" flush="false" />
	</footer>
	<!-- Footer End -->
</body>
</html>