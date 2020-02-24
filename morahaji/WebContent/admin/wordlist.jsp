<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style>
.table td{
font-size:14px;}

.table{
margin-bottom:70px;
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
.abc{
min-height:850px;}
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
	<div class="abc">
	<c:if test="${userId=='admin'}">
	<%int i=0; %>
		<div class="container">
			<div class="row">
				<div class="col-md">
					<form action="report_list.admin">
						<div class="input-group">
							<select id="viewcount1" name="search_field">
								<option value="0" selected>단어키</option>
								<option value="1">단어명</option>
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
									<th colspan="2">신고 단어 리스트</th>
									<th class="rightside" colspan="5"><font size=3>단어 수
											: ${listcount}</font></th>
								</tr>
								<tr>
									<td>번호</td>
									<td>단어명</td>
									<td>단어작성자 이름</td>
									<td>단어키</td>
									<td>신고수</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="u" items="${totallist}">
									<c:if test="${u.REPORTCOUNT > 3}">
										<tr class="alarm">
										<td>${u.RNUM}</td>
											
											<td><a href="report_info.admin?wordkey=${u.WORD_KEY}">${u.WORD_TITLE}</a></td>
											<td>${u.USER_NAME}</td>
											<td>${u.WORD_KEY}</td>
											<td>${u.REPORTCOUNT}</td>
										</tr>
									</c:if>
									<c:if test="${u.REPORTCOUNT <=3}">
										<tr class="normal">
										<td>${u.RNUM}</td>
											<td><a href="report_info.admin?wordkey=${u.WORD_KEY}">${u.WORD_TITLE}</a></td>
											<td>${u.USER_NAME}</td>
											<td>${u.WORD_KEY}</td>
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
												href="report_list.admin?page=${page-1}&search_field=${search_field}&search_word=${search_word}"
												class="page-link">이전</a>&nbsp;</li>
										</c:if>

										<c:forEach var="a" begin="${startpage}" end="${endpage}">
											<c:if test="${a == page }">
												<li class="page-item"><a class="page-link current"
													href="#">${a}</a></li>
											</c:if>
											<c:if test="${a != page }">
												<li class="page-item"><a
													href="report_list.admin?page=${a}&search_field=${search_field}&search_word=${search_word}"
													class="page-link">${a}</a></li>
											</c:if>
										</c:forEach>

										<c:if test="${page >= maxpage }">
											<li class="page-item"><a class="page-link current"
												href="#">&nbsp;다음</a></li>
										</c:if>
										<c:if test="${page < maxpage }">
											<li class="page-item"><a
												href="report_list.admin?page=${page+1}&search_field=${search_field}&search_word=${search_word}"
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
	</div>
</body>
