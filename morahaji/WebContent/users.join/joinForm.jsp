<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<style>
   .cbody{height:1000px; width:70%; padding:0; font-size:12px;}
   .formBody {margin: 5% auto 15% auto; width: 50%; padding: 0;}
   .tag_h1{text-align:center;font-weight:bold; padding-bottom:20px;}
   .tag_a{color:#69AAFFF; font-size:12px;}
   .tag_a:hover{text-decoration:none;}
   .tag_p{text-align:center;}
   #userId, #userPw, #userPwCheck, #userName, #userEmail, select{width:100%; float:center; height:3rem;}
   input[type=submit]{width:100%; height:3rem;}
   .modal-body{overflow-y: scroll; max-height:50%; height:200px;}
</style>
<script>
   $(document).ready(function() {
      $('#userId').blur(function(){
         var idVal = $('#userId').val().trim();
         pattern = /^[a-zA-Z0-9]{4,}$/;
         // 첫 글자 = 대문자, 두번째부터 대소문자, 숫자 _ 로 4개 이상
         if (!pattern.test(idVal)) {
            $('#idCheck').html("ID는 대소문자와 숫자로 구성된 4글자 이상이여야 합니다.").css('color', '#F25454');
            $("#userId").addClass("form-control is-invalid");
         } else {
            $.ajax({
               type : "post",
               url : "idcheck.net",
               data : {
                  "userId" : idVal
               },
               success : function(resp) {
                  if (resp == -1){
                     $('#idCheck').html("사용 가능한 아이디입니다.").css('color', '#73A839');
                    $("#userId").removeClass("is-invalid");
                    $("#userId").addClass("is-valid");
                  }
                  else if (resp == 1){
                     $('#idCheck').html("중복 아이디입니다.").css('color', '#F25454');
                     $("#userId").addClass("is-invalid");
                  }
               }, //success end
               error : function(request, status, error) {
   				location.href = "error/error.jsp";
               }
            }); // end ajax
         } // end else
      });   // end id Blur

      $('#userEmail').blur(function() { // 유효성 검사하는 함수
         var userEmailVal = $('#userEmail').val().trim();
         var pattern = /^\w+@\w+[.]\w{3}$/;

         if(!pattern.test(userEmailVal)){
            $('#emailCheck').text("!유효한 이메일 주소를 입력해주세요.").css('color', '#F25454');
            $("#userEmail").addClass("is-invalid");
         }else{
            $.ajax({
               type : "post",
               url : "emailcheck.net",
               data : {
                  "userEmail" : userEmailVal
               },
               success : function(resp) {
                  if (resp == -1){
                     $('#emailCheck').html("사용 가능한 이메일입니다.").css('color', '#73A839');
                     $("#userEmail").removeClass("is-invalid");
                     $("#userEmail").addClass("is-valid");
                  } else if (resp == 1){
                     $('#emailCheck').html("중복 이메일입니다.").css('color', '#F25454');
                     $("#userEmail").addClass("is-invalid");}
               }, //success end
               error : function(request, status, error) {
   				location.href = "error/error.jsp";
               }
            }) // end ajax
         }
      }); // end email Blur
   
      $('#userPw').blur(function() { // 유효성 검사하는 함수
         var userPwLength = $('#userPw').val().trim().length; 
         if (userPwLength < 6) {
            $('#pwCheck').text('!최소 6개의 문자를 사용해주세요.').css('color', '#F25454');
            $("#userPw").addClass("is-invalid");
         }else{
            $('#pwCheck').html('&nbsp;');
            $("#userPw").removeClass("is-invalid");
            $("#userPw").addClass("is-valid");
         }
      }); // end password Blur
      
      $('#userPwCheck').keyup(function(){
         var pw = $('#userPw').val().trim();
         var pwCheck = $('#userPwCheck').val().trim();
         if(pw != pwCheck){
            $('#pwCheckCheck').text('!비밀번호가 일치하지 않습니다.').css('color', '#F25454');
            $("#userPwCheck").addClass("is-invalid");
         }else{
            $('#pwCheckCheck').html('&nbsp;');
            $("#userPwCheck").removeClass("is-invalid");
            $("#userPwCheck").addClass("is-valid");
         }
      }); // end passwordCheck keyup
   })
   
   function beforeSubmit(){
    	  var age = $('#userAgeRange').val();
    	  var policy = $('#policy').is(":checked");
    	  
    	  $('#userAgeRange').removeClass("is-invalid");
		  $('#ageCheck').text('');
		  $('#policyCheck').text('');
		  
    	  if(age == 'none'){
    		  $('#userAgeRange').addClass("is-invalid");
    		  $('#ageCheck').text('!연령을 선택해주세요.').css('color', '#F25454');
    		  return false;
    	  }else if(!policy){
    		  $('#ageCheck').text('!약관을 확인해주세요.').css('color', '#F25454');
    		  return false;
     	  } else if($("input").hasClass("is-invalid")){
    		  $('#policyCheck').text('!내용을 확인해주세요.').css('color', '#F25454');
     		  return false;
     	  } else {
    		   /* window.location.href = "joinProcess.net";  */
    		  return true;
    	  }
      }// end form
</script>
</head>
<body>
<div class = "container cbody">
   <form action=joinProcess.net method=post onsubmit="return beforeSubmit()">
   <h1 class=tag_h1>회원가입</h1>
   <p  class=tag_p>이미 회원이신가요? <a href="login.net" class=tag_a>로그인</a></p>
      <input type=text id=userId name=userId class="form-control" placeholder=id required>
      <span id=idCheck>&nbsp;</span><br> 
      <input type=password id=userPw name=userPw class="form-control" placeholder=pass required>
      <span id=pwCheck>&nbsp;</span><br>
      <input type=password id=userPwCheck name=userPwCheck class="form-control" placeholder=passCheck required>
      <span id=pwCheckCheck>&nbsp;</span><br>
      <input type=text id=userName name=userName class="form-control" placeholder=name maxlength="50" required>
      <span id=nameCheck>&nbsp;</span><br> 
      <input type=text id=userEmail name=userEmail class="form-control" placeholder=email required>
      <span id=emailCheck>&nbsp;</span><br>
      <select id=userAgeRange name=userAgeRange class="form-control" required>
         <option value=none>연령 선택</option>
         <option value=10>10대</option>
         <option value=20>20대</option>
         <option value=30>30대</option>
         <option value=40>40대</option>
         <option value=50>50대</option>
         <option value=60>60대</option>
         <option value=ext>기타</option>
      </select><br> 
      <span id=ageCheck>&nbsp;</span><br> 
      <p class=tag_p><input type=checkbox id=policy> 
      <a class=tag_a href="#" data-toggle="modal" data-target="#myModal1">서비스 약관</a>과 
      <a class=tag_a href="#" data-toggle="modal" data-target="#myModal2">개인정보 보호정책</a>에 동의합니다.</p><br> 
      <span id=policyCheck>&nbsp;</span>
      <input type=submit value=회원가입 class = "btn btn-secondary">
   </form>
   <div class="modal" id="myModal1" >
    <div class="modal-dialog" >
      <div class="modal-content"> 
         <!-- Modal Header -->
        <div class="modal-header">
        <h4 class="modal-title">서비스 약관</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
         
         <!-- Modal body -->
        <div class="modal-body">
         <jsp:include page = "../textFiles/joinPolicy1.txt"/>
      </div>
      
         <!-- Modal footer -->
         <div class="modal-footer">
        <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  
   <div class="modal" id="myModal2" >
    <div class="modal-dialog" >
      <div class="modal-content"> 
         <!-- Modal Header -->
        <div class="modal-header">
        <h4 class="modal-title">개인정보 보호 정책</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
         
         <!-- Modal body -->
        <div class="modal-body">
         <jsp:include page = "../textFiles/joinPolicy2.txt"/>
      </div>
      
         <!-- Modal footer -->
         <div class="modal-footer">
        <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>  
</div>
</body>
</html>