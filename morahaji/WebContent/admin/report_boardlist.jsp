<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style>
.table td{
font-size:14px;}

.table{
margin-bottom:100px;
}

.table tr th {
	border-style: none;
}

.rightside {
	text-align: right;
}

.pagination {
	justify-content: center;
	margin: 20px
}

.alarm {
	background-color: #FBCFD0;
}

.input-group {
	width: 50%;
}

.current {
	color: gray;
}

.none{
margin-top:50px;}
.min_height{
min-height: 850px;
}
</style>
<script>
	$(function() {
		//검색 클릭 후 응답화면에는 검색시 선택한 필드가 선택되도록 합니다.
		var selectedValue = '${search_field}'
		if (selectedValue != '-1')
			$("#viewcount1").val(selectedValue);

		//검색어 공백 유효성 검사합니다.
		$(".admin_search").click(function() {
			if ($(".searchbox").val() == '') {
				alert("검색어를 입력하세요");
				return false;
			}
		});
		$("#viewcount1").change(function() {
			selectedValue = $(this).val();
			$("input").val('');
			if (selectedValue == '0') {
				$("input").attr("placeholder", "단어키를 숫자로 입력");
			}
		})

	})
</script>
</head>
<body>
	<c:if test="${empty userId}">
		<script>
			location.href = "login.net";
		</script>
	</c:if>
	<c:if test="${userId!='admin'}">
		<script>
			location.href = "main.index";
		</script>
	</c:if>
	<c:if test="${userId=='admin'}">

		<div class="container min_height">
			<div class="row">
				<div class="col-md">
					<form action="report_list_board.admin">
						<div class="input-group">
							<select id="viewcount1" name="search_field">
								<option value="0" selected>자유게시글 키</option>
								<option value="1">자유게시글 제목</option>
							</select> <input name="search_word" type="text"
								class="form-control searchbox" placeholder="Search"
								value="${search_word}">
							<button class="btn btn-primary submitbtn admin_search" type="submit">검색</button>
						</div>
					</form>
					<%-- 신고리스트가 있는 경우 --%>
					<c:if test="${listcount > 0 }">
						<table class="table">
							<thead>
								<tr>
									<th colspan="2">신고 자유게시글 리스트</th>
									<th class="rightside" colspan="4"><font size=3>글 수
											: ${listcount}</font></th>
								</tr>
								<tr>
									<td>자유게시글 키</td>
									<td>자유게시글 제목</td>
									<td>자유게시글 작성자</td>
									<td>자유게시글 신고수</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="u" items="${totallist}">
									<c:if test="${u.REPORTCOUNT > 3}">
										<tr class="alarm">
											<td>${u.BOARD_KEY}
											</td>
											<td><a href="report_info_board.admin?boardkey=${u.BOARD_KEY}">${u.BOARD_TITLE}</a>
											<td><a href="BoardSearch.bo?search_field=3&search_word=${u.USER_NAME}">${u.USER_NAME}</a></td>
											<td>${u.REPORTCOUNT}</td>
										</tr>
									</c:if>
									<c:if test="${u.REPORTCOUNT <=3}">
										<tr class="normal">
											<td>${u.BOARD_KEY}
											</td>
											<td><a href="report_info_board.admin?boardkey=${u.BOARD_KEY}">${u.BOARD_TITLE}</a>
											<td><a href="BoardSearch.bo?search_field=3&search_word=${u.USER_NAME}">${u.USER_NAME}</a></td>
											<td>${u.REPORTCOUNT}</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
						<div class="center-block pagebar">
							<div class="row">
								<div class="col">
									<ul class="pagination">
										<c:if test="${page <= 1 }">
											<li class="page-item"><a class="page-link current"
												href="#">이전&nbsp;</a></li>
										</c:if>
										<c:if test="${page > 1 }">
											<li class="page-item"><a
												href="report_list_board.admin?page=${page-1}&search_field=${search_field}&search_word=${search_word}"
												class="page-link">이전</a>&nbsp;</li>
										</c:if>

										<c:forEach var="a" begin="${startpage}" end="${endpage}">
											<c:if test="${a == page }">
												<li class="page-item"><a class="page-link current"
													href="#">${a}</a></li>
											</c:if>
											<c:if test="${a != page }">
												<li class="page-item"><a
													href="report_list_board.admin?page=${a}&search_field=${search_field}&search_word=${search_word}"
													class="page-link">${a}</a></li>
											</c:if>
										</c:forEach>

										<c:if test="${page >= maxpage }">
											<li class="page-item"><a class="page-link current"
												href="#">&nbsp;다음</a></li>
										</c:if>
										<c:if test="${page < maxpage }">
											<li class="page-item"><a
												href="report_list_board.admin?page=${page+1}&search_field=${search_field}&search_word=${search_word}"
												class="page-link ">&nbsp;다음</a></li>
										</c:if>
									</ul>
								</div>
							</div>
						</div>
					</c:if>


					<%-- 검색결과가 없는 경우 --%>
					<c:if test="${listcount == 0 }">
						<p class="none">검색 결과가 없습니다.</p>
					</c:if>

				</div>
			</div>
		</div>
	</c:if>
</body>
