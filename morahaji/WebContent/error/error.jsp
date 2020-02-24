<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 파비콘 -->
<link rel="shortcut icon" href="img/pavicon.ico">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<!-- 파비콘 -->
<meta charset="UTF-8">
<style>
#errorPage {
	text-align: center;
	width: 100%;
	height: 80%;
	margin-bottom: 50px;
	min-height: 800px;
}

#errorImg {
	height: 100%;
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
		<button class=historyBack>돌아가기</button>
		<br> <a href="../main.index"> <img id=errorImg
			src="../img/error.gif" title="메인 페이지로 이동">
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