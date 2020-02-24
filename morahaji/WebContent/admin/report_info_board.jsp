<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
footer{clear:both;}
</style>
</head>
<body>
	<!-- Header Start -->
	<nav>
		<jsp:include page="../index/header.jsp" flush="false" /><br> <br>
	</nav>
	<!-- Header End -->

	<!-- Body Start -->
			
	<jsp:include page="boardinfo.jsp" />
	
	<!-- Body End -->

	<!-- Footer Start -->
	<footer>
		<jsp:include page="../index/bottom.jsp" flush="false" />
	</footer>
	<!-- Footer End -->
</body>
