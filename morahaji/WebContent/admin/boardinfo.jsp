<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style>
table caption {
	color: #343A40;
	font-size: 20px;
	caption-side: top;
	text-align: center;
	caption-side: top;
}

.table {
	margin-bottom: 100px;
}

.back {
	text-align: left;
	margin-left: 20px;
}

.title {
	text-align: center;
	margin-bottom: 30px;
}

.table {
	text-align: left;
	font-size: 14px;
}

.wordtable {
	margin-bottom: 30px;
}

.bold {
	font-size: 14px;
	font-weight: bold;
}
.min_height{
min-height: 850px;
}
</style>
<script>
	
</script>
</head>
<body>
		<div class="container min_height">
			<div class="row">
				<div class="col-md">
					<a href="report_list_board.admin" class="back">리스트로 돌아가기</a>
					<h4 class="title">신고 상세 정보</h4>
					<c:set var="w" value="${boardinfo}" />
					<div class="container">
						<br>
						<table class="table table-bordered wordtable">
							<tbody>
								<tr>
									<td><b>자유게시글 키</b></td>
									<td>${w.BOARD_KEY}</td>
									<td><b>자유게시글 제목</b></td>
									<td>${w.BOARD_TITLE}</td>
								</tr>
								<tr>
									<td colspan="4"><b>자유게시글 내용</b></td>
								</tr>
								<tr>
									<td colspan="4">${w.BOARD_CONTENT}</td>
								</tr>
								<c:if test="${w.BOARD_GIF != null }">
									<tr>
										<td colspan="4"><b>자유게시판 GIF</b></td>
									</tr>
									<tr>
										<td colspan="4" align="center"><img src="${w.BOARD_GIF}"></td>
									</tr>
								</c:if>
								<tr>
									<td colspan="2"><b>삭제하기</b></td>
									<td colspan="2"><a
										href="deleteBoard.admin?boardkey=${w.BOARD_KEY}">삭제</a></td>
								</tr>
							</tbody>
						</table>
						<div class="bold">
							<b>신고 목록</b>
						</div>
						<br>
						<c:forEach var="de" items="${detailinfo}">
							<c:if test="${de.BOARD_KEY != null}">
								<table class="table table-bordered">
									<tr>
										<td><b>신고한 유저명</b></td>
										<td>${de.USER_NAME}</td>
										<td><b>신고 날짜</b></td>
										<td>${de.REPORT_DATE}</td>
									</tr>
									<tr>
										<td><b>신고이유</b></td>
										<td colspan="3">${de.REPORT_REASON}</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
</body>
