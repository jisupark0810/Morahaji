<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
#myword, #bmkword {
	background:#fff;
	width: 50%;
	color: #a6a6a6;
	border: none;
	position: relative;
	height: 40px;
	padding: 0;
	cursor: pointer;
	outline: none;
	font-size:16px;
}

#myword:hover, #bmkword:hover {
	background: #fff;
	border-bottom: 2px solid rgb(65, 133, 233);
	color: rgb(65, 133, 233)
}

.word_content {
	display: block;
	width: 90%;
	line-height: 3em
}

.wordGif {
	width:225px;
	height: 150px;
	margin: 2em 0;
}

.wordbox {
	border: 1px solid rgba(0,0,0,.125);
	padding: 0px 20px;
	border-radius: .25rem;
	margin:10px 0;
	line-height: 3em;
	box-sizing:borderbox;
	padding: 1.5em
}

.word_title {
	color: #4185E9;
	font-size: 20px;
	font-weight: bold;
	line-height: 3em;
	vertical-align: middle;
}

.pagination {
	justify-content: center;
	margin: 3em
}


.word_tag {
	
	color: #4288E5;
	display: inline;
}
#body{
	min-height: 600px;
}

.word_date{background-color: #FFFF80; font-size:12px;}
.Ex{font-weight: bold; color: #99ccff}
.By{font-weight: bold; color: #a6a6a6}

.word_content{font-size:16px;}
.like, .dislike{font-size:1.5em; color:#a6a6a6 }
</style>
<script>
	$(function() {
		$('.content').load("users.info/myword.jsp");
		$('#myword').css('font-weight','bold');
		$('#myword').css('color','black');
		$('#bmkword').css('font-weight','');
		
		
		$('#myword').click(function() {
			$('.content').load("users.info/myword.jsp");
			$('#myword').css('color','black');
			$('#myword').css('font-weight','bold');
			$('#bmkword').css('font-weight','');
			$('#bmkword').css('color','#a6a6a6');
		});
		$('#bmkword').click(function() {
			$('.content').load("users.info/bookmark.jsp");
			$('#bmkword').css('font-weight','bold');
			$('#bmkword').css('color','black');
			$('#myword').css('font-weight','');
			$('#myword').css('color','#a6a6a6');
		});

	});
</script>
</head>
<body>
<div id=body>
	<div class="row">
		<button class="col-3 mypageMenuSel" id="myword" >나의 단어</button>
		<button class="col-3 mypageMenuSel" id="bmkword" >북마크</button>
	</div>
	<div class="content"></div>
 </div>

</body>
