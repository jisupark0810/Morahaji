<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="users.info/bookmark.js"></script>
<style>
#bookmark_list {
	font-size: 30px;
	font-weight: bold;
	line-height: 2em;
}

.bookmark {
	color: #ADB5BD;
	vertical-align: middle;
	line-height: 2em;
	cursor: pointer;
	font-size: 2em;
}

.yellow {
	color: #FADF38;
}

.selectedBlue{
color: #4F689A;
}
.thumbsup:hover, .thumbsdown:hover{
color: #4F689A;
}
</style>


</head>
<body>

	<div class="row">
		<div class="col">
			<!-- <span id="bookmark_list">북마크한 단어</span> -->
		</div>
	</div>
	<div id="wordboxAppend"></div>

	<div class="row">
		<div class="col">
			<ul class="pagination pagination-sm" id="pempty2"></ul>
		</div>
	</div>



</body>
