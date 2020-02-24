<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.h4, h4 {
	font-size: 20px;
}

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

.summary {
	color: gray;
}

.forsummary {
	margin-top: 10px;
	font-size: 12pt;
}

.forwriter {
	font-color: black;
	font-size: 12pt;
}

.datecolor {
	color: #595959;
}

.wrap{
	margin-bottom:200px;
}
.forreplycount{
color: orange;
}
.nowrite{
text-align:center}
.cbody {height: 1000px;padding: 0;}

.time1, .ad1 {
	display: block;
}

.time2, .time3, .ad2, .ad3 {
	display: none;
}

.best_add {
	color: #a6a6a6; cursor : pointer;
	text-align: center;
	font-size: 10px;
	cursor: pointer;
}

.t {
	text-decoration: none;
	color: black;
}

.rankingLi {
	list-style: none;
	margin: 0;
	padding: 0;
}

.rankingLi>li {
	padding-left: 10%;
}

#rank {
	color: #a6a6a6;
	text-decoration:
}

#rankingTitle {
	text-align: center;
}

.rightcard{
margin-right: 0;
}

.min_height{
min-height: 1100px;
}

.current {
	color: gray;
}

</style>
<script>
var body = document.body;
function view(arg) {
   $(".time1, .time2, .time3, .time4, .ad1, .ad2, .ad3, .ad4").css(
         "display", "none");
   if (arg == "0") {
      $(".time2, .ad2").css("display", "block");
      viewcount = 1;
   } else if (arg == "1") {
      $(".time3, .ad3").css("display", "block");
      viewcount = 2;
   } else if (arg == "2") {
      $(".time1, .ad1").css("display", "block");
      viewcount = 0;
   }
}
var viewcount = 0;
var rtcarousel = setInterval(function() {
   view(viewcount)
}, 5000);

$("#best_search").mouseenter(function() {
   clearInterval(rtcarousel);
});

$("#best_search").mouseleave(function() {
   rtcarousel = setInterval(function() {
      view(viewcount)
   }, 5000);
});
function go(page) {
   var data="";
   var data = "state=mine&page=" + page;
   ajax(data);
}   
function setPaging(href, digit) {
output += "<li class=page-item>";
gray = "";
if (href == "") {
   gray = "gray";
}
anchor = "<a class='page-link " + gray + "'" + href + ">" + digit
      + "</a></li>";
output += anchor;
}
function ajax(data) {
   console.log(data)
   output = "";
   $.ajax({
      type : "post",
      url : "BoardList.bo",
      data : data, // 응답받은 data 값
      dataType : "json",
      cache : false,
      success : function(data) {
         $("#viewcount").val(data.limit);
         $("table").find("font").text("글 개수:" + data.listcount);
         if (data.listcount > 0) { // 총 갯수가 1개이상인 경우
            $("tbody").remove();
            var num = data.listcount - (data.page - 1) * data.limit;
            console.log(num)
            output = "<tbody>";
            $(data.boardlist).each(
                  function(index, item) {
                     output += '<tr><td><div class="media">';
                     output += '<div class="media-body"><span class="media-meta pull-right">';
                     output += item.BOARD_DATE+'</span>';
                     output += '<h4 class="title">';
                     output += '<a href="BoardDetailAction.bo?num='+item.BOARD_KEY+'">'+item.BOARD_TITLE;
                     output += '</a>';
                     if(item.BOARD_REPLYCOUNT>0){
                        output += '<span class="forreplycount">['+item.BOARD_REPLYCOUNT+']</span>';
                     }
                     output += '</h4><div id="forsummary"><span class="summary">글쓴이: '+item.USER_NAME+' · 조회수 : '+item.BOARD_READCOUNT+' · 추천수 : '+item.BOARD_LIKECOUNT+'</span></div>';
                     output += '</div></div></td></tr>'
                  })// each end
            output += "</tbody>"
            
            $('table').append(output)// table 완성

            $('#pempty').empty(); // 페이징 처리
            output="";
            digit = '이전&nbsp;'
            href = "";
            if (data.page > 1) {
               href = 'href=javascript:go(' + (data.page - 1) + ')';
            }
            setPaging(href, digit);

            for (var i = data.startpage; i <= data.endpage; i++) {
               digit = i;
               href = "";
               if (i != data.page) {
                  href = 'href=javascript:go(' + i + ')';
               }
               setPaging(href, digit);
            }

            digit = '다음&nbsp;';
            href = "";
            if (data.page < data.maxpage) {
               href = 'href=javascript:go(' + (data.page + 1) + ')';
            }
            setPaging(href, digit);

            $('#pempty').append(output)
         }// if(data.listcount > 0) end
         else {
            $("tbody").remove();
            $('#pempty').empty();
            nowrite="";
            nowrite+="<div class='center'>";
            nowrite+="<font size=5>등록된 글이 없습니다.</font>";
            nowrite+="</div>";
            $("#pempty").append(nowrite);
         }
      },// success end
      error : function() {
         console.log('에러')
      }
   })// ajax
}
$(function(){
   
   $("#write").click(function (){
         if("${userKey}" != "null"){
            location.href = "BoardWrite.bo";
         } else {
            location.href = "login.net";
         }
      })
   $("#box-1").change(function() {
      if($("#box-1").is(":checked")==true){
         go(1);
      }else{
         location.href="BoardList.bo";
      }
   })   
})

</script>
<body>
	<div class="wrap min_height">
		<div class="container cbody ">
			<div class="row">
				<div class="col-md titlebar">
					<h2>
						<b>자유게시판</b>
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md">
					<div class="pull-right">
						<div class="boxes">
							<c:if test="${!empty userKey}">
                        <c:if test="${userKey!=1}">
                           <input type="checkbox" id="box-1">
                           <label for="box-1">내가 쓴 글 보기 </label>
                        </c:if>
                     </c:if>
							<button class="btn boardwrite" id="write">글쓰기</button>
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
										<tbody>
											<!-- 글 tr시작 -->
											<c:forEach var="b" items="${boardlist}">
												<tr>
													<td>
														<div class="media">
															<div class="media-body">
																<span class="media-meta pull-right datecolor">${b.BOARD_DATE}</span>
																<h4 class="title">
																	<a href="BoardDetailAction.bo?num=${b.BOARD_KEY}">${b.BOARD_TITLE}
																	</a> <c:if test="${b.BOARD_REPLYCOUNT>0}"><span class="forreplycount">[${b.BOARD_REPLYCOUNT}]</span></c:if>
																</h4>
																<div class="forsummary">
																	<span class="summary">글쓴이: ${b.USER_NAME} · 조회수 : ${b.BOARD_READCOUNT} · 추천수 : ${b.BOARD_LIKECOUNT}</span>
																</div>
															</div>
														</div>
													</td>
												</tr>
												<!-- 글 tr끝 -->

											</c:forEach>
										</tbody>
									</table>
									<!-- //여기에 페이지네이션 들어와야함 -->
									<div class="row">
										<div class="col">
											<ul class="pagination pagination-sm" id="pempty">
												<c:if test="${page <= 1 }">
													<li class="page-item"><a class="page-link" href="#">이전&nbsp;</a>
													</li>
												</c:if>
												<c:if test="${page > 1 }">
													<li class="page-item"><a
														href="BoardList.bo?page=${page-1 }" class="page-link">이전</a>&nbsp;
													</li>
												</c:if>

												<c:forEach var="a" begin="${startpage }" end="${endpage }">
													<c:if test="${a == page }">
														<li class="page-item"><a class="page-link current" href="#">${a }</a>
														</li>
													</c:if>
													<c:if test="${a != page }">
														<li class="page-item"><a
															href="BoardList.bo?page=${a }" class="page-link">${a }</a></li>
													</c:if>
												</c:forEach>

												<c:if test="${page >= maxpage }">
													<li class="page-item"><a class="page-link" href="#">&nbsp;다음</a>
													</li>
												</c:if>
												<c:if test="${page < maxpage }">
													<li class="page-item"><a
														href="BoardList.bo?page=${page+1 }" class="page-link">&nbsp;다음</a>
													</li>
												</c:if>
											</ul>
										</div>
									</div>
								</c:if>
								<%--게시글이 없는 경우 --%>
								<c:if test="${listcount==0 }">
									<div class="nowrite">
									<font size=5>등록된 글이 없습니다.</font>
									</div>
								</c:if>
								<br>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${listcount!=0 }">
				<!-- 현재 인기글 시작 -->
			<div class="col-sm-4">
				<div class="card rightcard" style="width: 18rem;">
					<div class="card-body">
						<h5 id=rankingTitle>
							<span class="card-title"><b>👑 RANKING 👑</b></span>
						</h5>
						<br>
						<div id="wrapranking">
							<div class="containerranking">
								<ul class="rankingLi" id="best_search">
									<li class=rankingLi><c:set var="classNum" value="1" /> <c:forEach
											var="r" items="${ranking}">
											<dl class="time${classNum}">
												<c:forEach var="subranking" items="${r}">
													<dd>
														<a class="t"
															href="BoardDetailAction.bo?num=${subranking.BOARD_KEY}"><i
															id=rank>${subranking.RNUM}.</i> ${subranking.BOARD_TITLE}</a>
													</dd>
												</c:forEach>
											</dl>
											<c:set var="classNum" value="${classNum+1}" />
										</c:forEach></li>
									<li class=rankingLi><a class="best_add ad1"
										onClick="javascript:view('0')"> 5 ~ 10위 보기 <img
											src="img/right-arrow.png"></a> <a class="best_add ad2"
										onClick="javascript:view('1')"> 11 ~ 15위 보기 <img
											src="img/right-arrow.png"></a> <a class="best_add ad3"
										onClick="javascript:view('2')"> <img
											src="img/left-arrow.png"> 1 ~ 5위 보기
									</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 현재 인기글 끝 -->
			</c:if>
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
	</div>
</body>
