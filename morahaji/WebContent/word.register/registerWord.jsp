<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style>
.cbody2 {
	min-height: 1500px;
	padding: 0;
	font-family: "맑은 고딕";
	font-size: 12px;
}

.formBody {
	margin: 5% auto 15% auto;
	width: 50%;
	padding: 0;
}

h1 {
	text-align: center;
	font-weight: bold;
	padding-bottom: 20px;
}

#word_title, #word_tag, #word_example {
	width: 100%;
	float: center;
	height: 3rem;
}

#word_content {
	width: 100%;
	height: 10rem;
}

input[type=submit], input[type=reset] {
	width: 49%;
	height: 3rem;
}

.tag_a {
	color: #69AAFFF;
	font-size: 12px;
}

.tag_a:hover {
	text-decoration: none;
}

.modal-body {
	overflow-y: scroll;
	max-height: 50%;
	height: 200px;
}

#results>div>img {
	width: 100%;
	height: 100%;
}

.gif-box:hover {
	opacity: 60%;
}

.gif-box {
	padding: 1%;
	width: 50%;
	height: inherit;
	visibility: visible;
	display: inline-block;
}

#gif_search_text {
	width: 70%;
	float: left;
}

#gif_find {
	width: 30%;
	float: left;
}

#results {
	overflow: auto;
	height: 10rem;
	width: 100%;
}

#buttons {
	clear: both;
}
</style>
<script>
	
</script>
</head>
<body>
	<div class="container cbody2">
		<form class="formBody" action=registerWordProccess.wd method=post
			onkeydown="return event.key != 'Enter';">
			<h1>새로운 단어 등록</h1>
			<p>
				단어입력란입니다.<br> 모라하지에 있는 단어는 모두 평범한 사람들이 적었습니다.<br> 자신만의 단어를
				넣어보세요.(필수)
			</p>
			<input type=text class="form-control" id=word_title name=word_title
				placeholder="단어" required> <br> <br>
			<p>
				단어의 설명을 적는 곳입니다.<br> 설명은 주로 일반적인 단어를 사용해주세요.(필수)
			</p>
			<textarea id=word_content class="form-control" name=word_content
				placeholder="설명" required></textarea>
			<br> <br>
			<p>다른 사람이 이해하기 쉬운 예시를 들어주세요.(필수)</p>
			<textarea id=word_example class="form-control" name=word_example
				placeholder="예시" required></textarea>
			<br> <br>
			<p>
				태그를 추가해주세요.<br>(예시: #모라하지 #신조어사전 #인싸사전)
			</p>
			<input type=text id=word_tag class="form-control" name=word_tag
				placeholder="#태그"> <br> <br>

			<p>단어를 표현하기 위해 gif를 추가하실 수 있습니다.</p>
			<!-- giphy -->
			<div class="title-search-bar">
				<div id="buttons"></div>
				<div class="search-bar">
					<input type="text" name="query" id="gif_search_text"
						class="form-control" /> <input type="button" id="gif_find"
						value="찾기" class="btn btn-outline-secondary" />
				</div>
			</div>
			<hr>
			<div id="results"></div>
			<input type=hidden id=word_gif name=word_gif>
			<!-- giphy 끝 -->

			<br> <br> <br>
			<p>
				단어를 등록하시면<br> 모라하지의 <a href="#" data-toggle="modal"
					data-target="#myModal1" class=tag_a>서비스 약관</a>에 동의한 것으로 처리됩니다.
			</p>
			<br> <br> <input type=submit value=단어등록
				class="btn btn-dark">
				<input type=reset value=취소
				class="btn btn-secondary rgst_cancel">
		</form>
	</div>

	<div class="modal" id="myModal1">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">서비스 약관</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<jsp:include page="../textFiles/joinPolicy1.txt" />
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>

	<!-- myModal1 end -->
	</div>
</body>
