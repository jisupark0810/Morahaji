<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<head>
<style>
.cbody {height: 1000px;   padding: 0; font-size: 12px;}
.formBody {margin: 5% auto 15% auto;width: 50%;padding: 0;}
.tag_h1 {text-align: center;font-weight: bold;padding-bottom: 20px;}
#userId, #userEmail {width: 100%;float: center;height: 3rem;}
.tag_p{text-align:center;}
#tempPass{border:1px solid black; height:3rem; margin: 5% auto 5% auto;width: 90%; padding: 0; 
   text-align:center; line-height:3rem;}
.tab_submit {width: 100%;height: 3rem;}
.tag_button {margin: 5% auto 15% auto;width: 70%;height: 3rem;}
</style>
<script>
   $(document).ready(function() {
      $('form').submit(function() {
         var userId = $('#userId').val();
         var userEmail = $('#userEmail').val();
         $.ajax({
            type : "post",
            url : "findPwProcess.net",
            data : {
               "userId" : userId,
               "userEmail" : userEmail
            },
             success : function(resp) {
                      if (resp.length > 4) { // 임시 비밀번호 발급
                    	  if(resp == "error"){
              				location.href = "error/error.jsp";
                    	  }
                          $('#idCheck').html("&nbsp;");
                          $("#userId").removeClass("is-invalid");
                          $("#userId").addClass("form-control is-valid");
                          $('#emailCheck').html("&nbsp;");
                          $("#userEmail").removeClass("is-invalid");
                          $("#userEmail").addClass("form-control is-valid");
                          var tempPw = resp.split('/'); 
                          $("#tempPass").html(tempPw[1]);//임시 비밀번호
                          
                          // show modal = show 비밀번호
                          $('#tempPass').html(tempPw[1]);
                          jQuery("#myModal3").modal('show');
                          
                      } else if (resp == 0) { // 아이디 / 이메일 없음
                         $('#idCheck').text("!아이디가 존재하지 않습니다.").css('color', '#F25454');
                         $('#userId').removeClass("is-valid");
                         $('#userId').addClass("form-control is-invalid");
                         $('#emailCheck').text("!이메일이 존재하지 않습니다.").css('color', '#F25454');
                         $('#userEmail').removeClass("is-valid");
                         $('#userEmail').addClass("form-control is-invalid");
                      } else if (resp == -1) { // 이메일 불일치
                         $('#idCheck').html("&nbsp;");
                         $("#userId").removeClass("is-invalid");
                         $("#userId").addClass("form-control is-valid");
                         $('#emailCheck').text("!이메일이 일치하지 않습니다.").css('color', '#F25454');
                         $('#userEmail').removeClass("is-valid");
                         $('#userEmail').addClass("form-control is-invalid");
                      } else if (resp == -2) { // 아이디 불일치
                         $('#idCheck').text("!아이디가 일치하지 않습니다.").css('color', '#F25454');
                         $('#userId').removeClass("is-valid");
                         $('#userId').addClass("form-control is-invalid");
                         $('#emailCheck').html("&nbsp;");
                         $("#userEmail").removeClass("is-valid");
                         $("#userEmail").addClass("form-control is-valid");
                      } else if (resp == -3) { // 아이디 / 이메일 서로 불일치
                         $('#idCheck').text("!서로 맞지 않는 값입니다.").css('color', '#F25454');
                         $('#userId').removeClass("is-valid");
                         $('#userId').addClass("form-control is-invalid");
                         $('#emailCheck').html("&nbsp;").css('color', '#F25454');
                         $('#userEmail').removeClass("is-valid");
                         $('#userEmail').addClass("form-control is-invalid");
                      }
                   },
            error : function(request, status, error) {
				location.href = "error/error.jsp";
            }
         }) // end ajax
      })// end submit
   })

</script>
</head>
<body>
<div class = "container cbody">
   <form action=findPwProcess.net method=post class = "formBody" onsubmit="return false;">
      <h1 class=tag_h1>비밀번호 찾기</h1>
      <p class=tag_p>회원가입시 등록한 아이디와 이메일을 입력하시면<br>
      <p class=tag_p>임시 비밀번호를 발급합니다.</p>
      <input type=text id=userId name=userId placeholder=ID>
      <span id=idCheck>&nbsp;</span><br> 
      <input type=text id=userEmail name=userEmail placeholder=Email>
      <span id=emailCheck>&nbsp;</span><br> 
      <input type=submit value=제출 class="tab_submit btn btn-dark">
<!--       <input type=submit value=제출 class="btn btn-dark" data-toggle=modal data-target=#myModal3> -->
   </form>
</div>

<div class="modal" id="myModal3" >
    <div class="modal-dialog" >
      <div class="modal-content">
         <br><br>
         <h1 class=tag_h1>임시 비밀번호 발급</h1>
         <p class=tag_p>임시 비밀번호로 로그인하여 비밀번호를 변경해주세요</p>
         <p class=tag_p>임시 비밀번호</p>
         <div id = "tempPass"></div> 
        <button type="button" class="btn btn-dark tag_button" data-dismiss="modal">확인</button>
      </div>
    </div>
  </div>
</body>
