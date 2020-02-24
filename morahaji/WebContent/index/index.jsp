<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="../error/error_404.jsp"%>

<!DOCTYPE html>
<html>
<head>
<!-- 파비콘 -->
<link rel="shortcut icon" href="img/pavicon.ico">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<!-- 파비콘 -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.wrap{
min-height:100%;
position:relative;
margin-bottom:150px;
}
footer{clear:both;}
</style>
</head>
<body>
	<!-- Header Start -->
	<nav>
		<jsp:include page="header.jsp" flush="false" /><br> <br>
	</nav>
	<!-- Header End -->

	<!-- Body Start -->
			
	<jsp:include page="content.jsp" />
	
	<!-- Body End -->

	<!-- Footer Start -->
	<footer>
		<jsp:include page="bottom.jsp" flush="false" />
	</footer>
	<!-- Footer End -->
</body>
</html>