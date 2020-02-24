<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.pagination {
	justify-content: center;
	margin: 10px 10px 20px 10px;
}

.table-filter {
	background-color: #fff;
	border-bottom: 1px solid #eee;
}

.table-filter tbody tr:hover {
	cursor: pointer;
	background-color: #eee;
}

.table-filter tbody tr td {
	padding: 10px;
	vertical-align: middle;
	border-top-color: #eee;
}

.table-filter tbody tr.selected td {
	background-color: #eee;
}

.table-filter tr td:first-child {
	width: 38px;
}

.table-filter tr td:nth-child(2) {
	width: 35px;
}

.media {
	padding: 5px;
}

.media-body {
	margin-left: 15px;
}

.name {
	font-size: 14px;
	margin-right: 20px;
}

.maintitle {
	margin-left: 25px;
}

/*Checkboxes styles*/
input[type="checkbox"] {
	display: none;
}

input[type="checkbox"]+label {
	display: inline-block;
	position: relative;
	padding-left: 35px;
	margin-bottom: 30px;
	margin-right: 20px;
	font: 14px/20px 'Open Sans', Arial, sans-serif;
	color: #68A0EA;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	font: 14px/20px 'Open Sans', Arial, sans-serif;
}

input[type="checkbox"]+label:before {
	content: '';
	display: block;
	width: 20px;
	height: 20px;
	border: 1px solid #027EF6;
	position: absolute;
	left: 0;
	top: 0;
	opacity: .6;
	-webkit-transition: all .12s, border-color .08s;
	transition: all .12s, border-color .08s;
}

input[type="checkbox"]:checked+label:before {
	width: 10px;
	top: -5px;
	left: 5px;
	border-radius: 0;
	opacity: 1;
	border-top-color: transparent;
	border-left-color: transparent;
	-webkit-transform: rotate(45deg);
	transform: rotate(45deg);
}

.titlebar {
	margin-left: 25px;
}

.boardwrite:hover {
	opacity: 0.8;
	color: white;
}

.boardwrite {
	background: #4185E9;
	color: white;
	width: 150px;
	margin-bottom: 15px;
}

.input-group {
	width: 700px;
	margin: 0 auto;
	margin-bottom: 50px
}
#forcontent{
margin-top: 20px;
margin-bottom: 20px;}
.summary{
color:gray;
}
#forsummary{
margin-top:10px;}
.datecolor {
	color: #595959;
}
.forreplycount{
color: orange;
}
#nowrite{
text-align:center;}
.min_height{
min-height: 850px;
}

</style>
<script>
	$(function() {
		$("#toboard").click(function() {
			location.href = "BoardList.bo";
		})
		$("#search").click(function() {
			if ($("#searchtext").val() == '') {
				alert("검색어를 입력하세요");
				return false;
			}
		})
	})
</script>
<body>
	<div class="container min_height">
		<div class="row">
			<div class="col-md titlebar">
				<h2><b>자유게시판</b></h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md">
				<div class="pull-right">
					<div class="boxes">
						<button class="btn boardwrite" id="toboard">자유게시판으로</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md">
				<div class="panel">
					<div class="panel-body">
						<div class="table-container">
							<c:if test="${listcount>0}">
								<table class="table table-filter">
									<!-- 글 tr시작 -->
									<c:forEach var="t" items="${totallist}">
										<tr>
											<td>
												<div class="media">
													<div class="media-body">
														<span class="media-meta pull-right datecolor">${t.BOARD_DATE}</span>
														<h4 class="title">
															<c:if test="${t.BOARD_TITLE_SEARCH == false}">
																<a href="BoardDetailAction.bo?num=${t.BOARD_KEY}">${t.BOARD_TITLE_BEFORE}${t.BOARD_TITLE}${t.BOARD_TITLE_AFTER}
																</a><c:if test="${t.BOARD_REPLYCOUNT>0}"><span class="forreplycount">[${t.BOARD_REPLYCOUNT}]</span></c:if>
															</c:if>
															<c:if test="${t.BOARD_TITLE_SEARCH == true}">
																<a href="BoardDetailAction.bo?num=${t.BOARD_KEY}">${t.BOARD_TITLE_BEFORE}<b>${t.BOARD_TITLE}</b>${t.BOARD_TITLE_AFTER}
																</a><c:if test="${t.BOARD_REPLYCOUNT>0}"><span class="forreplycount">[${t.BOARD_REPLYCOUNT}]</span></c:if>
															</c:if>
														</h4>
														<c:if test="${t.BOARD_CONTENT_SEARCH ==false}">	
															<div id="forcontent">
															<span>${t.BOARD_CONTENT_BEFORE}${t.BOARD_CONTENT}${t.BOARD_CONTENT_AFTER}
															</span>
															</div>
														</c:if>
														<c:if test="${t.BOARD_CONTENT_SEARCH ==true}">
															<div id="forcontent">
															<span>${t.BOARD_CONTENT_BEFORE}<b>${t.BOARD_CONTENT}</b>${t.BOARD_CONTENT_AFTER}
															</span>
															</div>
														</c:if>
														<div id="forsummary">
																<span class="summary">글쓴이: ${t.USER_NAME} · 조회수 : ${t.BOARD_READCOUNT} · 추천수 : ${t.BOARD_LIKECOUNT}</span>
															</div>
													</div>
												</div>
											</td>
										</tr>
										<!-- 글 tr끝 -->
									</c:forEach>
								</table>
								<!-- //여기에 페이지네이션 들어와야함 -->
								<div class="row">
									<div class="col">
										<ul class="pagination pagination-sm">
											<c:if test="${page <= 1 }">
												<li class="page-item"><a class="page-link" href="#">이전&nbsp;</a>
												</li>
											</c:if>
											<c:if test="${page > 1 }">
												<li class="page-item"><a
													href="report.bo?page=${page-1 }" class="page-link">이전</a>&nbsp;</li>
											</c:if>

											<c:forEach var="a" begin="${startpage }" end="${endpage }">
												<c:if test="${a == page }">
													<li class="page-item"><a class="page-link" href="#">${a }</a>
													</li>
												</c:if>
												<c:if test="${a != page }">
													<li class="page-item"><a href="report.bo?page=${a }"
														class="page-link">${a }</a></li>
												</c:if>
											</c:forEach>

											<c:if test="${page >= maxpage }">
												<li class="page-item"><a class="page-link" href="#">&nbsp;다음</a>
												</li>
											</c:if>
											<c:if test="${page < maxpage }">
												<li class="page-item"><a
													href="report.bo?page=${page+1 }" class="page-link">&nbsp;다음</a></li>
											</c:if>
										</ul>
									</div>
								</div>

							</c:if>
							<%--게시글이 없는 경우 --%>
							<c:if test="${listcount==0 }">
								<div id="nowrite">
								<font size=5>검색된 글이 없습니다.</font>
								</div>
							</c:if>
							<br>
						</div>
					</div>
				</div>
			</div>
		</div>

	<form action="BoardSearch.bo">
		<div class="input-group">
			<select name="search_field">
				<option value="0" selected>제목</option>
				<option value="1">내용</option>
				<option value="2">제목+내용</option>
				<option value="3">작성자</option>
			</select> <input name="search_word" type="text" class="form-control"
				placeholder="Search" id="searchtext" value="${search_word }">
			<button class="btn btn-primary" type="submit" id="search">검색</button>
		</div>
	</form>
	</div>

</body>
