<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<!-- 파비콘 -->
<link rel="shortcut icon" href="img/pavicon.ico">
<link rel="icon" href="img/favicon.ico" type="image/x-icon">
<!-- 파비콘 -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
			
	<jsp:include page="userlist.jsp" />
	
	<!-- Body End -->

	<!-- Footer Start -->
	<footer>
		<jsp:include page="../index/bottom.jsp" flush="false" />
	</footer>
	<!-- Footer End -->
</body>
