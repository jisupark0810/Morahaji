<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>


.cbody {
	min-height: 1300px;
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

#BOARD_TITLE, #BOARD_CONTENT {
	width: 100%;
	float: center;
	height: 3rem;
}

#BOARD_CONTENT {
	width: 100%;
	height: 10rem;
}

input[type=submit], input[type=reset] {
	width: 49%;
	height: 3rem;
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
	padding : 1%;
	width: 50%;
	height: 50%;
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
	height: 15rem;
	width: 100%;
}

#buttons {
	clear: both;
}

.red {
	color: #FD1B28;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<body>
	<div class="container cbody">
		<form action="BoardAddAction.bo" name="boardform" class="formBody"
			method=post>
			<h1>글쓰기</h1>
			<p>
				제목 <span class="red">*</span>
			</p>
			<input type=text class="form-control" id="BOARD_TITLE"
				name="BOARD_TITLE" placeholder="제목" required> <br> <br>
			<p>
				내용 <span class="red">*</span>
			</p>
			<textarea id=BOARD_CONTENT class="form-control" name=BOARD_CONTENT
				placeholder="내용" required></textarea>
			<br> <br>

			<p>gif 추가 (선택)</p>
			<!-- giphy -->
			<script src="js/giphy.js"></script>
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
			<input type=hidden id=board_gif name=board_gif>
			<!-- giphy 끝 -->
			<br> <br> <br> <input type=submit
				value=글등록 class="btn btn-dark"> <input type=reset value=취소
				class="btn btn-secondary">
		</form>
	</div>
</body>
</html>