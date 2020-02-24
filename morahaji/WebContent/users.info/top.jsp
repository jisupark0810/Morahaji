<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#myinfo_update {
	border-radius: 10px;
	float: right;
}

#profile-photo {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	margin: 10px
}

.top {
	background: lightgray;
	padding: 15px;
	font-size: 12px;
	color: black;
	
}
.bordertop{border-radius:.25rem .25rem 0 0}
.borderbottom{border-radius:0 0 .25rem .25rem;
text-align: center;

}

#myinfo_update {
	background: #4185E9;
	color: #fff;
	border: none;
	position: relative;
	height: 30px;
	padding: 0 2em;
	cursor: pointer;
	transition: 800ms ease all;
	outline: none;
}

#myinfo_update:hover {
	background: rgba(0, 0, 0, 0);
	color: #4185E9;
}

#myinfo_update:before, #myinfo_update:after {
	content: '';
	position: absolute;
	top: 0;
	right: 0;
	height: 2px;
	width: 0;
	background: #4185E9;
	transition: 400ms ease all;
}

#myinfo_update:after {
	right: inherit;
	top: inherit;
	left: 0;
	bottom: 0;
}

#myinfo_update:hover:before, #myinfo_update:hover:after {
	width: 100%;
	transition: 800ms ease all;
}
.mypageUserName{font-size:20px; }
</style>
<script>
	$(document).ready(function() {
		var userKey = '${userKey}';
		console.log("userkey : " + userKey);
		$.ajax({
			type : "post",
			url : "mypageLoad.net",
			data : {
				"userKey" : userKey
			},
			success : function(resp) {
				if (resp.length > 5) { // 값 가져옴
					var result = resp.split('/');
					$("#last_activity_date").html(result[0]);//마지막 활동일
					$("#reg_word_count").html(result[1]);//등록한 단어 수
				} else if (resp == -1) { // 아이디 없음
					alert("오류");
				}
			},
			error : function(request, status, error) {
				alert("error : " + error);
			}
		}) // end ajax
	/** 박선아 수정 */
	$("#myinfo_update").click(function() {
		location.href = "update.net";
	});
	/** 박선아 수정 끝*/
	})
</script>
</head>
<body>
	<div class="row top bordertop">
		<div class="col">
			<p>
				<img src="img/profile.png" id="profile-photo" class="setProfilePhoto"> <span id="name" class='mypageUserName'>
					${userName}</span>
				<button id="myinfo_update">나의 정보 수정</button>
			</p>
		</div>
	</div>
	<div class="row top borderbottom">
		<div class="col-3">
			<span id="reg_word">등록된 단어수</span> <br> <span
				id="reg_word_count"></span>
		</div>
		<div class="col-3">
			<span id="last_activity">마지막 활동일</span> <br> <span
				id="last_activity_date"></span>
		</div>
	</div>
</body>
