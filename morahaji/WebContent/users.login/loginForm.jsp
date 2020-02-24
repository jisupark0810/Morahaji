<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<style>
.cbody {
	height: 1000px;
	padding: 0;
	font-size: 12px;
}

.formBody {
	margin: 5% auto 15% auto;
	width: 50%;
	padding: 0;
}

.tag_h1 {
	text-align: center;
	font-weight: bold;
	padding-bottom: 20px;
}

#userId, #userPw {
	width: 100%;
	float: center;
	height: 3rem;
}

.tag_a {
	color: #69AAFFF;
	font-size: 12px;
	float: right;
}

.tag_a:hover {
	text-decoration: none;
}

#idRemember {
	float: left;
}

.tag_a::second-child {
	clear: both;
}


input[type=submit] {
	width: 100%;
	height: 3rem;
}
   /* #idCheck, #pwCheck{color:white;} */
</style>
<script>
	$(document).ready(function() {

		function checkFunc() { // 유효성 검사하는 함수
			var check = 0;
			var idLength = $('#userId').val().trim().length;
			var pwLength = $('#userPw').val().trim().length;

			if (idLength == 0) {
				$('#idCheck').text("!아이디를 입력하세요.").css('color', '#F25454');
				 $("#userId").addClass("form-control is-invalid");
				check++;
			} else {
				$('#idCheck').html("&nbsp;");
				$('#userId').removeClass("is-invalid");
			}
			if (pwLength == 0) {
				$('#pwCheck').text("!비밀번호를 입력하세요.").css('color', '#F25454');
				 $("#userPw").addClass("form-control is-invalid");
				check++;
			} else {
				$('#pwCheck').html("&nbsp;");
				$('#userPw').removeClass("is-invalid");
			}
			return check;
		} // end checkFunc
		
		$('#userPw').keypress(function(e) { 
		    var s = String.fromCharCode( e.which );
		    if ( s.toUpperCase() === s && s.toLowerCase() !== s && !e.shiftKey ) {
		    	 $('#pwCheck').text("!Caps Lock을 확인해주세요").css('color', '#F25454');
                 $("#userPw").addClass("form-control is-invalid");
		    }else{
		    	$('#pwCheck').html("&nbsp;");
		    	$('#userPw').removeClass("is-invalid");
		    }
		});
		
		$('form').submit(function() {
			var userId = $('#userId').val().trim();
			var userPw = $('#userPw').val().trim();
			var emptyCheck = checkFunc();
			if (emptyCheck == 0) {
				$.ajax({
					type : "post",
					url : "loginProcess.net",
					data : {
						"userId" : userId,
						"userPw" : userPw
					},
					success : function(resp) {
			               if (resp == 0) { // 비밀번호 불일치
			            	   $('#idCheck').html("&nbsp;");
			                  $('#userId').addClass("is-valid");
			                  $('#userId').removeClass("is-invalid");
			                  $('#pwCheck').text("!비밀번호가 틀렸습니다.").css('color', '#F25454');
			                  $("#userPw").addClass("is-invalid");
			                  $("#userPw").val("");
			               } else if (resp == -1) { // 아이디 없음
			            	  $('#pwCheck').html("&nbsp;");
			                  $('#userId').removeClass("is-invalid");
			                  $('#idCheck').text("!아이디가 존재하지 않습니다.").css('color', '#F25454');
			                  $("#userId").addClass("is-invalid");
			               } else if(resp == -2){
			            	   $('#pwCheck').html("&nbsp;");
				                  $('#userId').removeClass("is-invalid");
				                  $('#idCheck').text("!탈퇴한 회원입니다.").css('color', '#F25454');
				                  $("#userId").addClass("is-invalid");
			               }else if (resp == 1) { // 로그인 완료 
			            	   window.location.href = "main.index";
				           }
			            },
					error : function(request, status, error) {
						location.href = "error/error.jsp";
					}
				}) // end ajax
			}
		})// end submit

		//ID 저장 - 쿠키 사용 (7일간)
			 
		    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
		    var key = getCookie("key");
		    $("#userId").val(key); 
		     
		    if($("#userId").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
		        $("#idRememeber").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
		    }
		     
		    $("#idRememeber").change(function(){ // 체크박스에 변화가 있다면,
		        if($("#idRememeber").is(":checked")){ // ID 저장하기 체크했을 때,
		            setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
		        }else{ // ID 저장하기 체크 해제 시,
		            deleteCookie("key");
		        }
		    });
		     
		    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
		    $("#userId").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
		        if($("#idRememeber").is(":checked")){ // ID 저장하기를 체크한 상태라면,
		            setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
		        }
		    });

	})
		 
		function setCookie(cookieName, value, exdays){
		    var exdate = new Date();
		    exdate.setDate(exdate.getDate() + exdays);
		    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
		    document.cookie = cookieName + "=" + cookieValue;
		}
		 
		function deleteCookie(cookieName){
		    var expireDate = new Date();
		    expireDate.setDate(expireDate.getDate() - 1);
		    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
		}
		 
		function getCookie(cookieName) {
		    cookieName = cookieName + '=';
		    var cookieData = document.cookie;
		    var start = cookieData.indexOf(cookieName);
		    var cookieValue = '';
		    if(start != -1){
		        start += cookieName.length;
		        var end = cookieData.indexOf(';', start);
		        if(end == -1)end = cookieData.length;
		        cookieValue = cookieData.substring(start, end);
		    }
		    return unescape(cookieValue);
		}
</script>
</head>
<body>
<div class = "container cbody">
   <form name=loginform  class = "formBody" method=post onsubmit="return false;">
      <h1 class=tag_h1>로그인</h1>
      <input type=text id=userId name=userId class="form-control" placeholder="ID / Email">
      <span id=idCheck>&nbsp;</span><br>
      <input type=password id=userPw name=userPw  class="form-control" placeholder="Password">
      <span id=pwCheck>&nbsp;</span><br>
       <a title="7일동안 저장합니다."><input type=checkbox id=idRememeber name=idRemamber>&nbsp;ID 저장</a>
      <a href="findPw.net" class=tag_a>비밀번호 찾기</a><br><br>
      <input type=submit value=로그인 class="btn btn-dark"><br><br>
      <p>모라하지는 처음이세요?<a href="join.net" class=tag_a>회원가입</a></p>      
   </form>
   </div>
</body>
</html>


