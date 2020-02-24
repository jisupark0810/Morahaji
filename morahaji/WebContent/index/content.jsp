<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.card-title {
	color: #4185E9;
}

.container {
	margin-bottom: 20px;
}

.container:after {
	clear: both;
}

.word_date {
	background: #FFFF80;
	font-size: 12px;
}

.btngroup1 {
	padding: 25px 25px 0 25px;
}

.btngroup2 {
	padding: 25px;
	position: relative;
}

.bookmark {
	color: #ADB5BD;
}

.yellow {
	color: #FADF38;
}

.report {
	color: #ADB5BD;
}

.red {
	color: #FE8F80;
}

.like {
	color: #ADB5BD;
}

.like:hover {
	color: #4F689A;
}

.dislike {
	color: #ADB5BD;
}

.dislike:hover {
	color: #4F689A;
}

.twitter {
	color: #ADB5BD;
}

.twitter:hover {
	color: #0FADED;
}

.facebook {
	color: #ADB5BD;
}

.facebook:hover {
	color: #4269AF;
}

.card-title {
	font-weight: bold;
}

.fa-2x {
	font-size: 1.5em;
}

.word {
	background: #4185E9;
	color: white;
	width: 250px;
}

.word:hover {
	opacity: 0.8;
	color: white;
}

.row {
	margin-bottom: 25px;
}

.setting {
	color: #ADB5BD;
	cursor: pointer;
}

.setting:hover {
	color: #343A40;
}

.nbsp {
	float: right;
}

@media ( max-width : 800px) {
	.rightcard {
		display: none;
	}
}

.settingmenu {
	background: #F1F3F4;
	float: right;
}

#settingbar {
	width: 150px;
	position: absolute;
	bottom: 30px;
	right: 0px;
}

.menuBox {
	position: absolute;
	width: 30px;
	height: 30px;
	right: 26px;
	top: 24px;
	display: inline-block
}

input {
	display: none;
}

input:checked+label>i {
	color: #4F689A;
}

.likeOncolor>i {
	color: #4F689A;
}

.likeBox {
	display: inline-block;
}

.likeDiv {
	display: inline-block;
}

.dislikeDiv {
	display: inline-block;
}

.snsBox {
	display: inline-block;
}

.word_tag {
	font-size: 16px;
	color: #4288E5;
	display: inline;
}

.pagination {
	justify-content: center;
	margin: 10px
}

.card {
	margin-bottom: 20px;
}

.card-img-top {
	width: 60%;
	text-align: center;
	margin: auto;
}

#papagoLogo {
	text-align: center;
	max-height: 300px;
	max-width: 300px;
	padding: 3%;
}

#papagoBtn {
	text-align: center;
	width: 100%;
	margin-top: 1%;
	margin-bottom: 1%;
}

#translatedText {
	border: none;
	text-align: center;
	overflow: auto;
}

#translateText {
	border: none;
}

.tag_img {
	max-width: 30px;
	max-height: 30px;
}

.word_name {
	font-size: 14px;
	color: #4F6998;
	display: inline;
}

.current {
	color: gray;
}

.Ex {
	font-weight: bold;
	color: #99ccff
}

.By {
	font-weight: bold;
	color: #a6a6a6
}
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

.textCenter{
	text-align: center;
}
</style>
</head>
<body>
	<div class="wrap">
		<div class="container">
			<c:if test="${listcount == 0 }">
				<div class="row">
					<!-- 단어 세부내용 시작 -->
					<div class="col-sm-8">
						<div class="card rightcard" style="width: 18rem;"></div>
						<p>등록된 단어가 없습니다.</p>
					</div>
					<!-- 단어 세부내용 끝 -->
					<!-- 단어 등록하기 카드 시작 -->
					<div class="col-sm">
						<div class="card rightcard" style="width: 18rem;">
							<div class="card-body">
								<h5 class="card-title textCenter">단어를 등록해주세요.</h5>
								<p class="card-text">모라하지의 단어는 사용자분의 참여로 만들어집니다.</p>
								<a href="javascript:void(0);" onclick="registerWord();"
									class="btn word"><i class="fa fa-plus"></i>&nbsp; 단어 등록하기</a>
							</div>
						</div>
						<!-- 단어 등록하기 카드 끝 -->
						<!-- 파파고 번역 시작-->
						<div class="card rightcard">
							<img class="card-img-top" id=papagoLogo src="img/Papago_logo.png"
								alt="Papago logo">
							<div class="card-body">
								<textarea id=translateText class="form-control mr-sm-2"
									placeholder="번역할 내용을 입력하세요."></textarea>
								<button class="btn word" id=papagoBtn onclick="papago();">
									<img src="img/translation.png" class=tag_img> <span>&nbsp;
										번역하기</span>
								</button>
								<textarea id=translatedText class="form-control mr-sm-2"
									readonly></textarea>
							</div>
						</div>
						<!-- 파파고 번역 끝 -->
					</div>
				</div>
			</c:if>
			<c:if test="${listcount > 0}">
				<div class="row">
					<!-- 단어 세부내용 시작 -->
					<div class="col-sm-8">
						<c:forEach var="word" items="${wordList}">
							<div class="card">
								<div class="btngroup1">
									<span class="card-text word_date"
										id="word_date_${word.WORD_KEY}">${word.WORD_DATE}</span>
									<c:if test="${!word.BOOKMARK}">
										<i class="fa fa-bookmark fa-2x fa-pull-right bookmark"
											id="bookmark_${word.WORD_KEY}"></i>
									</c:if>
									<c:if test="${word.BOOKMARK}">
										<i class="fa fa-bookmark fa-2x fa-pull-right bookmark yellow"
											id="bookmark_${word.WORD_KEY}"></i>
									</c:if>
									<span class="nbsp">&nbsp;</span>
									<c:if test="${word.REPORT}">
										<i
											class="fa fa-exclamation-triangle fa-2x fa-pull-right report red"
											id="report_${word.WORD_KEY}"></i>
									</c:if>
									<c:if test="${!word.REPORT}">
										<i
											class="fa fa-exclamation-triangle fa-2x fa-pull-right report"
											id="report_${word.WORD_KEY}"></i>
									</c:if>
								</div>
								<div class="card-body">
									<h4 class="card-title word_title"
										id="word_title_${word.WORD_KEY}">${word.WORD_TITLE}</h4>
									<p class="card-text word_content"
										id="word_content_${word.WORD_KEY}">${word.WORD_CONTENT}</p>
									<p class="card-text word_example"
										id="word_example_${word.WORD_KEY}">
										<b class='Ex'>Ex. </b>${word.WORD_EXSENTENCE}</p>
									<c:if test="${word.WORD_HASHTAG != null}">
										<!-- hashtag 시작  -->
										<c:forEach var="tag" items="${word.WORD_HASHTAG}">
											<p class="card-text word_tag" id="word_tag_${word.WORD_KEY}">
												<a href="main.index?tag=${tag}">#${tag}&nbsp;</a>
											</p>
										</c:forEach>
										<!-- hashtag 끝  -->
									</c:if>
								</div>
								<c:if test="${word.WORD_GIF != null}">
									<!-- GIF 영역 시작 -->
									<img class="card-img-top" src="${word.WORD_GIF}"
										id="word_gif_${word.WORD_KEY}" alt="Card image cap">
									<!-- GIF 영역 끝 -->
								</c:if>
								<div class="card-body">
									<p class="card-text word_name" id="word_name_${word.WORD_KEY}">
										<b class='By'>By. </b> ${word.WRITER_NAME}
									</p>
								</div>
								<div class="btngroup2">
									<div class="likeBox">
										<c:if test="${!word.LIKE}">
											<div class="likeDiv">
												<i class="fa fa-thumbs-up fa-2x like"></i> <span
													class="likenumber" id="likenumber_${word.WORD_KEY}">${word.LIKECOUNT}</span>
											</div>
										</c:if>
										<c:if test="${word.LIKE}">
											<div class="likeDiv likeOncolor > i">
												<i class="fa fa-thumbs-up fa-2x like"></i> <span
													class="likenumber" id="likenumber_${word.WORD_KEY}">${word.LIKECOUNT}</span>
											</div>
										</c:if>

										<c:if test="${!word.HATE}">
											<div class="dislikeDiv">
												<i class="fa fa-thumbs-down fa-2x dislike"></i> <span
													class="dislikenumber" id="dislikenumber_${word.WORD_KEY}">${word.HATECOUNT}</span>
											</div>
										</c:if>
										<c:if test="${word.HATE}">
											<div class="dislikeDiv likeOncolor > i">
												<i class="fa fa-thumbs-down fa-2x dislike"></i> <span
													class="dislikenumber" id="dislikenumber_${word.WORD_KEY}">${word.HATECOUNT}</span>
											</div>
										</c:if>

									</div>
									<span>&nbsp;</span>
									<div class="snsBox">
										<i class="fa fa-twitter-square fa-2x twitter"
											onclick="window.open('https://twitter.com/intent/tweet?url=http://localhost:8088/morahaji/main.index?keyword=${word.WORD_TITLE}','','width=700, height=400'); return false;"></i>
										<i class="fa fa-facebook-square fa-2x facebook"
											onclick="window.open('http://www.facebook.com/sharer/sharer.php?u=http://localhost:8088/morahaji/main.index?keyword=${word.WORD_TITLE}','','width=700, height=400'); return false;">
										</i>
									</div>
									<!-- 수정/삭제 모달 시작 -->
									<c:if
										test="${word.USER_KEY == sessionScope.userKey || sessionScope.userId == 'admin'}">

										<div class="menuBox">
											<i class="fa fa-cog fa-2x fa-pull-right setting "
												id="i_${word.WORD_KEY}"></i>
											<div id="settingbar" class="menu1" style="display: none">
												<ul class="nav flex-column settingmenu">
													<li class="nav-item"><a class="nav-link"
														href="modifyWord.wd?wordKey=${word.WORD_KEY}" id="edit"
														class="edit">수정</a></li>
													<li class="nav-item"><a class="nav-link" href="#"
														id="delete_${word.WORD_KEY}" class="delete"
														data-toggle="modal" data-target="#deletemodal">삭제</a></li>
												</ul>
											</div>
										</div>
									</c:if>
									<!-- 수정/삭제 모달 끝 -->
								</div>
							</div>

						</c:forEach>
					</div>
					<!-- 단어 세부내용 끝 -->
					<!-- 단어 등록하기 카드 시작 -->
					<div class="col-sm">
						<div class="card rightcard" style="width: 18rem;">
							<div class="card-body">
								<h5 class="card-title textCenter">단어를 등록해주세요.</h5>
								<p class="card-text">모라하지의 단어는 사용자분의 참여로 만들어집니다.</p>
								<a href="javascript:void(0);" onclick="registerWord();"
									class="btn word"><i class="fa fa-plus"></i>&nbsp; 단어 등록하기</a>
							</div>
						</div>
						<!-- 단어 등록하기 카드 끝 -->
						<!-- 파파고 번역 시작-->
						<div class="card rightcard" style="width: 18rem;">
							<img class="card-img-top" id=papagoLogo src="img/Papago_logo.png"
								alt="Papago logo">
							<div class="card-body">
								<textarea id=translateText class="form-control mr-sm-2"
									placeholder="번역할 내용을 입력하세요."></textarea>
								<button class="btn word" id=papagoBtn onclick="papago();">
									<img src="img/translation.png" class=tag_img> <span>&nbsp;
										번역하기</span>
								</button>
								<textarea id=translatedText class="form-control mr-sm-2"
									readonly></textarea>
							</div>
						</div>
						<!-- 파파고 번역 끝 -->
						<!-- 현재 인기글 시작 -->
							<div class="card rightcard" style="width: 18rem;">
								<div class="card-body">
									<h5 class=textCenter>
										<span class="card-title"><b>👑 RANKING 👑</b></span>
									</h5>
									<br>
									<div id="wrapranking">
										<div class="containerranking">
											<ul class="rankingLi" id="best_search">
												<li class=rankingLi><c:set var="classNum" value="1" />
													<c:forEach var="r" items="${ranking}">
														<dl class="time${classNum}">
															<c:forEach var="subranking" items="${r}">
																<dd>
																	<a class="t"
																		href="main.index?keyword=${subranking.WORD_TITLE}"><i
																		id=rank>${subranking.RNUM}.</i>
																		${subranking.WORD_TITLE}</a>
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
						<!-- 현재 인기글 끝 -->
					</div>
				</div>
			</c:if>
			<!-- 페이지네이션 시작 -->
			<c:if test="${listcount > 0}">
				<!-- 검색, 해시태그 없을 경우 시작 -->
				<c:if test="${keyword == null && tag == null}">
					<div class=row>
						<div class="col-sm-8">
							<ul class="pagination pagination-sm">
								<c:if test="${page <= 1 }">
									<li class="page-item"><a class="page-link" href="#">◀</a></li>
								</c:if>
								<c:if test="${page > 1 }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page-1}">◀</a></li>
								</c:if>
								<c:forEach var="a" begin="${startpage}" end="${endpage}">
									<c:if test="${a == page}">
										<li class="page-item"><a class="page-link current"
											href="#">&nbsp;${a}&nbsp;</a></li>
									</c:if>
									<c:if test="${a != page}">
										<li class="page-item"><a href="main.index?page=${a}"
											class="page-link">${a}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${page >= maxpage }">
									<li class="page-item"><a class="page-link" href="#">▶</a></li>
								</c:if>
								<c:if test="${page < maxpage }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page+1}">▶</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</c:if>
				<!-- 검색, 해시태그 없을 경우  끝-->
				<!-- 검색 있을 경우 시작 -->
				<c:if test="${keyword != null}">
					<div class=row>
						<div class="col-sm-8">
							<ul class="pagination pagination-sm">
								<c:if test="${page <= 1 }">
									<li class="page-item"><a class="page-link" href="#">◀</a></li>
								</c:if>
								<c:if test="${page > 1 }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page-1}&keyword=${keyword}">◀</a></li>
								</c:if>
								<c:forEach var="a" begin="${startpage}" end="${endpage}">
									<c:if test="${a == page}">
										<li class="page-item"><a class="page-link current"
											href="#">&nbsp;${a}&nbsp;</a></li>
									</c:if>
									<c:if test="${a != page}">
										<li class="page-item"><a
											href="main.index?page=${a}&keyword=${keyword}"
											class="page-link">${a}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${page >= maxpage }">
									<li class="page-item"><a class="page-link" href="#">▶</a></li>
								</c:if>
								<c:if test="${page < maxpage }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page+1}&keyword=${keyword}">▶</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</c:if>
				<!-- 검색 있을 경우 끝 -->
				<!-- 해시태그 있을 경우 시작 -->
				<c:if test="${tag != null}">
					<div class=row>
						<div class="col-sm-8">
							<ul class="pagination pagination-sm">
								<c:if test="${page <= 1 }">
									<li class="page-item"><a class="page-link" href="#">◀</a></li>
								</c:if>
								<c:if test="${page > 1 }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page-1}&tag=${tag}">◀</a></li>
								</c:if>
								<c:forEach var="a" begin="${startpage}" end="${endpage}">
									<c:if test="${a == page}">
										<li class="page-item"><a class="page-link current"
											href="#">&nbsp;${a}&nbsp;</a></li>
									</c:if>
									<c:if test="${a != page}">
										<li class="page-item"><a
											href="main.index?page=${a}&tag=${tag}" class="page-link">${a}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${page >= maxpage }">
									<li class="page-item"><a class="page-link" href="#">▶</a></li>
								</c:if>
								<c:if test="${page < maxpage }">
									<li class="page-item"><a class="page-link"
										href="main.index?page=${page+1}&tag=${tag}">▶</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</c:if>
				<!-- 해시태그 있을 경우 끝 -->
			</c:if>
			<!-- 페이지네이션 끝 -->
		</div>
	</div>
	<div class="modal" id="deletemodal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Alert</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<!--x부분 data-dismiss는 모달 종료할때 쓰는것 modal을 종료시키겠다. -->
				</div>

				<!-- Modal body -->
				<div class="modal-body">정말로 삭제하시겠습니까?</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<form id=deletemodalForm action="deleteWord.wd" method="post">
						<input type=hidden name=wordKey value="" id=deleteWordKey>
						<input type="submit" class="btn btn-dark" data-dismiss="modal"
							id=deletemodalSubmit value=삭제>
					</form>
					<input type="button" class="btn btn-light" data-dismiss="modal"
						value=취소>
					<!-- 닫기버튼 -->
				</div>
			</div>
		</div>
	</div>



</body>
<script>
	var body = document.body;
	function view(arg) {
		$(".time1, .time2, .time3, .ad1, .ad2, .ad3").css("display", "none");
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
	}, 3000);

	$("#best_search").mouseenter(function() {
		clearInterval(rtcarousel);
	});

	$("#best_search").mouseleave(function() {
		rtcarousel = setInterval(function() {
			view(viewcount)
		}, 3000);
	});

	$(document).ready(function() {
		$(".menuBox").click(function() {
			$(this).find('#settingbar').toggle();
		})

		/* $(document).mouseup(function(e) {
			var container = $("#settingbar");
			if (container.has(e.target).length == 0)
				container.hide();
		}) */

		$('.bookmark').click(function() {
			if ($(this).hasClass('yellow')) { // 북마크 이미 된 상태
				$(this).toggleClass('yellow');
				var wordKey = $(this).attr('id');
				removeCount(wordKey, 'bookmark');
			} else { //북마크 되지 않은 상태
				if ("${userKey}" != "") {
					$(this).toggleClass('yellow');
					var wordKey = $(this).attr('id');
					addCount(wordKey, 'bookmark');
				} else {
					location.href = "login.net";
				}
			}
		})

		$('.report').click(function() {
			if ($(this).hasClass('red')) { // 신고 이미 된 상태
				alert("신고 취소되었습니다.");
				$(this).toggleClass('red');
				var wordKey = $(this).attr('id');
				removeReport(wordKey);
			} else { //신고 되지 않은 상태
				if ("${userKey}" != "") {
					var reason = "사유";
					while (reason == "사유") {
						reason = prompt("신고 사유를 입력해주세요.", "사유");
						if (!reason)
							return false;
					}
					$(this).toggleClass('red');
					var wordKey = $(this).attr('id');
					addReport(wordKey, reason);
				} else {
					location.href = "login.net";
				}
			}
		})
		$(".likeBox").ready(function() {
			$(".likeDiv").click(function() { // 좋아요
				var wordKey = $(this).find('span').attr('id');
				if ($(this).hasClass("likeOncolor > i")) { // 좋아요 취소
					$(this).removeClass("likeOncolor > i");
					removeCount(wordKey, 'like');
				} else { // 좋아요 안함
					if ($(this).next().hasClass("likeOncolor > i")) { // 싫어요 -> 좋아요
						$(this).next().removeClass("likeOncolor > i");
						$(this).toggleClass("likeOncolor > i");
						removeCount(wordKey, 'hate');
						addCount(wordKey, 'like');
					} else { // 좋아요
						$(this).toggleClass("likeOncolor > i");
						addCount(wordKey, 'like');
					}
				}
			})
			$(".dislikeDiv").click(function() { // 싫어요
				var wordKey = $(this).find('span').attr('id');
				if ($(this).hasClass("likeOncolor > i")) { // 싫어요 취소
					$(this).removeClass("likeOncolor > i");
					removeCount(wordKey, 'hate');
				} else {
					if ($(this).prev().hasClass("likeOncolor > i")) { // 좋아요 -> 싫어요
						$(this).prev().removeClass("likeOncolor > i")
						$(this).toggleClass("likeOncolor > i");
						removeCount(wordKey, 'like');
						addCount(wordKey, 'hate');
					} else { // 싫어요
						$(this).toggleClass("likeOncolor > i");
						addCount(wordKey, 'hate');
					}
				}
			})

		})

		$("#deletemodalSubmit").click(function() {
			$("#deletemodalForm").submit();
		})

		$(".menuBox > i").click(function() {
			var rowWordKey = $(this).attr("id");
			var wordKey = rowWordKey.split("_")[1];
			$("#deleteWordKey").val(wordKey);
		});
	})
	function addCount(wordKey, countType) {
		$
				.ajax({
					type : "post",
					url : "countAdd.action",
					data : {
						"postKey" : wordKey,
						"countType" : countType,
						"postType" : "word"
					},
					success : function(resp) {
						var wordKeyVal = wordKey.split('_')[1];
						if (countType == 'like') {
							var count = $('[id*=likenumber_' + wordKeyVal + ']')
									.html() * 1;
							$('[id=likenumber_' + wordKeyVal + ']').html(
									count + 1);
						} else if (countType == 'hate') {
							var count = $(
									'[id*=dislikenumber_' + wordKeyVal + ']')
									.html() * 1;
							$('[id=dislikenumber_' + wordKeyVal + ']').html(
									count + 1);
						}
						if (resp == "error")
							location.href = "error/error.jsp";
					},
					error : function(request, status, error) {
						location.href = "error/error.jsp";
					}
				}) // end ajax
	}
	function removeCount(wordKey, countType) {
		$
				.ajax({
					type : "post",
					url : "countRemove.action",
					data : {
						"postKey" : wordKey,
						"countType" : countType,
						"postType" : 'word'
					},
					success : function(resp) {
						var wordKeyVal = wordKey.split('_')[1];
						if (countType == 'like') {
							var count = $('[id*=likenumber_' + wordKeyVal + ']')
									.html() * 1;
							$('[id=likenumber_' + wordKeyVal + ']').html(
									count - 1);
						} else if (countType == 'hate') {
							var count = $(
									'[id*=dislikenumber_' + wordKeyVal + ']')
									.html() * 1;
							$('[id=dislikenumber_' + wordKeyVal + ']').html(
									count - 1);
						}
						if (resp == "error")
							location.href = "error/error.jsp";
					},
					error : function(request, status, error) {
						location.href = "error/error.jsp";
					}
				}) // end ajax
	}
	function addReport(wordKey, reason) {
		$.ajax({
			type : "post",
			url : "reportAdd.action",
			data : {
				"postKey" : wordKey,
				"reason" : reason,
				"postType" : "word"
			},
			success : function(resp) {
				if (resp == "error")
					location.href = "error/error.jsp";
			},
			error : function(request, status, error) {
				location.href = "error/error.jsp";
			}
		}) // end ajax
	}
	function removeReport(wordKey) {
		$.ajax({
			type : "post",
			url : "reportRemove.action",
			data : {
				"postKey" : wordKey,
				"postType" : 'word'
			},
			success : function(resp) {
				if (resp == "error")
					location.href = "error/error.jsp";
			},
			error : function(request, status, error) {
				location.href = "error/error.jsp";
			}
		}) // end ajax
	}

	function papago() {
		var text = $("#translateText").val();
		if (text.length == 0)
			return false;
		$.ajax({
			type : "post",
			url : "papago.wd",
			data : {
				"text" : text
			},
			success : function(resp) {
				$("#translatedText").val(resp);
				if (resp == "error")
					location.href = "error/error.jsp";
			},
			error : function(request, status, error) {
				location.href = "error/error.jsp";
			}
		})
	}
</script>
