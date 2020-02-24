<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
<style>/** 수정본 보내기 */
   .cbody {min-height: 800px;padding: 0; font-family: "맑은 고딕";font-size: 15px;}
   .naeyong{text-align:center;}
   .tag_btn{float:right; width:9rem; height:2rem;line-height:1rem; font-color:white;}
   #A, #nameChange, #emailChange, #passChange{text-decoration: none; float:right;}
   #A, #nameChange:hover, #emailChange:hover, #passChange:hover{text-decoration: none;}
   .gray{color:Gray; float:right;}
   /** 이미지 관련 style */
   #A{float:left;}
   .tagh1{text-align:center;}
   .profile_image{display:inline-block; max-width:160px; max-height:160px; 
      margin-left:10rem; margin-right:10rem; border-radius:50%;}
   .profile_icon{float:right;}
   
   .fa-camera{color: #ADB5BD;}
   .fa-2x {font-size: 2em;}
   /** 모달폼 관련 style */
   h1 {text-align: center;font-weight: bold;padding-bottom: 20px; font-family:"맑은 고딕"}   
   .modal-content{padding:30px;}
   #myModal4 > input, #myModal5 > input{width: 100%;height: 3rem;}   
   #myModal4 > p, #myModal5 > .tag_p{text-align:center;}
   #buttons > button {padding: 0; width:50%; float:right;}
   /** 글자색 회색으로 바꾸기*/
   .gray2{color:darkgray;}
   /**커서 모양 바꾸기*/
   button:disabled:hover{cursor:not-allowed;}
   #ageChange, #drop{cursor:pointer}
   #ageChange:hover, #drop:hover{color:lightblue}
   
   #upfile{display:none;}
   .center {text-align:center;}
   .cursor{cursor: pointer;}

</style>
<script>
   $(function(){
      //1. 이름 변경 모달 창 띄움
      $("#nameChange").click(function(){
         var newName = $("#newName").val();
         jQuery("#myModal4").modal('show');
      });
      
      //이름 변경
      $("#myModal4").find($("button[type=submit]")).click(function(){
         if ($("#newName").val() == "") {
            alert("새로운 이름을 입력해주세요.");
            $("#newName").focus();
         } else{
            var newName = $("#newName").val();
            $.ajax({//입력값이 들어가면 비동기로 전송
               type : "post",
                  url : "updateProcessAction.net",
                 data : {
                        "nameChange" : newName
                      },         
                  success : function(resp) {
                    alert("이름이 변경되었습니다.");
                     location.href='update.net';
                  },
                  error : function(request, status, error) {
                  location.href = "error/error.jsp";
                  }
            });   
         }//else end
      });//Modal4 > button click end
      
      //뒤로 가기 시 초기화
      $("#myModal4").find($("button[type=reset]")).click(function(){
            $("#newName").val('');
      });//button[type=reset] click end
      
      //마이페이지로 돌아가기
      $("#A").click(function(){
         location.href = "mypage.net";
      });
      
      //2. 이메일 변경 모달 창 띄움
      $("#emailChange").click(function(){
         var newEmail = $("#newEmail").val();
         jQuery("#myModal5").modal('show');
      });      
      
      //이메일 변경
      $("#myModal5").find($("button[type=submit]")).click(function(){
         if ($("#newEmail").val() == "") {
               alert("새로운 이메일을 입력해주세요.");
               $("#newEmail").focus();
               return false;
         } else {
            if ($("#newEmail").hasClass("is-invalid")) {
               alert("이메일 형식이 바르지 않습니다.");
               $("#newEmail").val('');
             return false;
         } else {
            
            
            var newEmail = $("#newEmail").val();
            $.ajax({//입력값이 들어가면 비동기로 전송
                type : "post",
                   url : "updateEmailProcessAction.net",
                   data : {
                      "emailChange" : newEmail
                       },         
                   success : function(resp) {
                    alert("이메일이 변경되었습니다.");
                     location.href='update.net';
                    },
                  error : function(request, status, error) {
                 location.href = "error/error.jsp";
                     }
            });//ajax end
         }   
         }
      });//click end   
      
      //이메일 변경시 유효성 검사
      $('#newEmail').keyup(function(){                    
                var newEmail = $('#newEmail').val().trim();
                var pattern = /^\w+@\w+[.]\w{3}$/;
               $("#myModal5").find($("button[type=submit]")).attr('disabled', false);

                if(!pattern.test(newEmail)){
                  $('#emailCheck').text("!유효한 이메일 주소를 입력해주세요.").css('color', '#F25454');
                  $("#newEmail").addClass("is-invalid");
                  $("#newEmail").removeClass("is-valid");
                } else {
                   $.ajax({
                      type : "post",
                      url : "emailcheck.net",
                      data : {
                         "userEmail" : newEmail
                      },
                      success : function(resp) {
                         if (resp == -1){
                           $('#emailCheck').html("사용 가능한 이메일입니다.").css('color', '#73A839');
                            $("#newEmail").removeClass("is-invalid");
                            $("#newEmail").addClass("is-valid");
                        } else if (resp == 1){
                            $('#emailCheck').html("중복 이메일입니다.").css('color', '#F25454');
                            $("#newEmail").addClass("is-invalid");
                            $("#newEmail").removeClass("is-valid");
                            $("#myModal5").find($("button[type=submit]")).attr('disabled', true);                            
                       }
                      }, //success end
                      error : function(request, status, error) {
                        location.href = "error/error.jsp";
                      }
                   }); // end ajax
                 }//else end
          }); // end email Blur          
        
       //뒤로 가기 시 초기화
      $("#myModal5").find($("button[type=reset]")).click(function(){
         $("#newEmail").val('');
         $("#emailCheck").html("&nbsp;");
      });//button[type=reset] click end
          
       
      //3. 비밀번호 변경 팝업
      $("#passChange").click(function(){
         jQuery("#myModal6").modal('show');
      });      
      
      $("#newPass").keyup(function(){
         if ($("#newPass").val().length < 6) {
            $("#newnewPass").html("! 최소 6개의 문자를 사용해주세요.").css('color', '#F25454');
            $("#newPass").addClass("is-invalid");
            $("#newPass").focus();
            return false;
         } else {            
            $("#newnewPass").html("&nbsp;");
            $("#newPass").removeClass("is-invalid");
            $("#newPass").addClass("is-valid");
         }
      });//newPass keyup end
      
      $("#newPassCheck").keyup(function(){
         if ($("#newPass").val() != $("#newPassCheck").val()) {
            $("#newnewPassCheck").html("! 비밀번호가 일치하지 않습니다.").css('color', '#F25454');
            $("#newPassCheck").addClass("is-invalid");
            $("#newPassCheck").focus();
            return false;
         } else {
            $("#newnewPassCheck").html("&nbsp;");
            $("#newPassCheck").removeClass("is-invalid");
            $("#newPassCheck").addClass("is-valid");
         }         
      });//newPassCheck blur      
      
      //submit 시 유효성 검사(공백 불가)
      $("#myModal6").find($("button[type=submit]")).click(function(){         
         if ($("#userPass").val() != "" && $("#newPass").val() != "" && $("#newPassCheck").val() != "") {//공백이 없을 때
            if ($("#userPass").val() != "${userinfo.USER_PASSWORD}") {
               $("#newuserPass").html("! 기존 비밀번호가 일치하지 않습니다.").css('color', '#F25454');
               $("#userPass").addClass("is-invalid");
               return false;
            } else if ($("#userPass").val() == "${userinfo.USER_PASSWORD}") {
               $("#newuserPass").html("");
               $("#userPass").addClass("is-valid");
            } 
           
           if ($("#newPass").hasClass("is-invalid") || $("#newPassCheck").hasClass("is-invalid")) {
             alert("비밀번호 형식이 바르지 않습니다.");
              $("#newPass").val('').removeClass('is-invalid').focus();
              $("#newnewPass").html("&nbsp;");
              $("#newPassCheck").val('').removeClass('is-invalid').removeClass('is-valid');
             return false;
         } else {
                   var newPass = $("#newPass").val();
                   $.ajax({
                      type : "post",
                      url : "updatePassProcessAction.net",
                      data : {
                            "userPass" : newPass
                      },
                      success : function(resp) {
                          alert("비밀번호가 변경되었습니다.");
                        location.href='update.net';
                      }, //success end
                      error : function(request, status, error) {
                        location.href = "error/error.jsp";
                      }
                }); // end ajax          
         }  
         } else {//공백이 하나라도 있을 때         
            if ($("#userPass").val() == "") {
               alert("현재 비밀번호를 입력해주세요.");
               $("#userPass").focus();
               return false;
            } else if ($("#newPass").val() == "") {
               $("#newnewPass").html("새로운 비밀번호를 입력해주세요.").css('color', '#F25454');
               $("#newPass").addClass("is-invalid");
               $("#newPass").focus();
               return false;
            }else if ($("#newPassCheck").val() == "") {
               $("#newnewPassCheck").html("새로운 비밀번호를 재입력해주세요.").css('color', '#F25454');
               $("#newPassCheck").addClass("is-invalid");
               $("#newPassCheck").focus();
               return false;
            }
         }//else end
      });//submit 시 유효성 검사(공백 불가) end
      
      $("#myModal6").find($("button[type=reset]")).click(function(){
         $("#userPass").val('');
         $("#newPass").val('');
         $("#newPassCheck").val('');
         
         $("#newuserPass").html("&nbsp;").removeClass("is-invalid");
         $("#newPass").html("&nbsp;").removeClass("is-valid");
         $("#newPass").html("&nbsp;").removeClass("is-invalid");
         $("#newPass").html("&nbsp;").removeClass("is-valid");
         $("#newPassCheck").html("&nbsp;").removeClass("is-invalid");
         $("#newPassCheck").html("&nbsp;").removeClass("is-valid");

      });//button[type=reset] click end
      
      
      //4. 연령대 변경
      $("#ageChange").click(function(){
         jQuery("#myModal7").modal('show');
      });//ageChange end
      
      //연령대 선택
      if ($("#userAgeBefore").text() == "ext") {
         $("#newAge").val($("#userAgeBefore").text());
         $("#userAgeBefore").text("기타연령");
      } else {
         $("#newAge").val($("#userAgeBefore").text());
      }      
      
      //연령대 변경
      $("#myModal7").find($("button[type=submit]")).click(function(){
         var newAge = $("option:selected").val();
         $.ajax({//입력값이 들어가면 비동기로 전송
            type : "post",
               url : "updateAgeProcessAction.net",
              data : {
                     "ageChange" : newAge
                   },//ageChange가 넘어가는 값         
               success : function(resp) {
                 alert("연령대가 변경되었습니다.");
                  location.href='update.net';
               },
               error : function(request, status, error) {
                 location.href = "error/error.jsp";
               }
         });
      });
      
      //6. 탈퇴하기
      $("#drop").click(function(){
         var answer = confirm("정말로 탈퇴하시겠습니까?");
         if (answer) {
            $.ajax({
               type : "post",
               url : "member_delete.net",
               success : function(){
                  location.href = "main.index";
               },
               error : function(request, status, error) {
                 location.href = "error/error.jsp";
                  }
            });
         }//if end
      });//drop end
      
   });
   
   //프로필 이미지 변경하기
    function reviewUploadImg(fileObj){
      $('#photoChange').submit();
   } 
</script>
</head>
<body>
<div class="container cbody">
   <!-- 상단 메뉴 -->
   <div class="row">
      <div class="col-md-4"><a href = "#" id = "A">마이페이지로 돌아가기</a></div>
      <div class="col-md-4"><h5>나의 정보</h5></div>
      <div class="col-md-4"></div>
   </div>
   <br>

   <!-- 화면 좌단 -->
   <div class="row">
      <div class="col-md-6">
      <p class = "gray2 tag_p">이름</p>
      <div><hr></div>
      <span id = "newnewName" class = "naeyong">${userName}</span>
      <button class = "btn btn-info tag_btn" id = "nameChange">이름 변경</button>
      <br><br><br><br>

      <p class = "gray2 tag_p">아이디</p>
      <div><hr></div>
      <span>${userId}</span>
      <br><br><br><br>
   
      <p class = "gray2 tag_p">이메일</p>
      <div><hr></div>
      <span class = "naeyong">${userEmail}</span>
      <button class = "btn btn-info tag_btn" id = "emailChange">이메일 변경</button>
      <br><br><br><br>
    
      <p class = "gray2 tag_p">비밀번호</p>
      <div><hr></div>
      <button class = "btn btn-info tag_btn" id = "passChange">비밀번호변경</button>
      <br><br><br><br>
      
      <p class = "gray2 tag_p">연령대</p>
      <div><hr></div>
      <span id = "userAgeBefore" class = "naeyong">${userinfo.USER_AGERANGE}</span>대
      <a class = "gray" id = "ageChange">수정</a>
      <br><br>
      </div>
      
      <!-- 화면 우단 -->
      <div class="col-md-6">
      <p class = "gray2 tag_p">프로필 사진</p>
      <div><hr></div>
      <form class = "profile" id = "photoChange" enctype = "multipart/form-data" 
      	method="post" action=updatePhotoAction.net >
         <label for = "upfile">
            <img id = "photovalue" class = "profile_image cursor" src="${userinfo.USER_PROFILEPHOTO}"><!-- 기본 이미지 -->
            <input type = "file" id = "upfile" name = "choicePhoto" accept = "image/gif, image/jpeg, image/png"
               onchange = "reviewUploadImg(this);"><!-- 파일 선택 --><br>
            <p class="center tag_p">이미지는 5MB까지 업로드 가능합니다.</p>
            <i class="fa fa-camera fa-2x like fa-pull-right profile cursor"></i>
         </label>      
      </form>
      <br><br><br><br>
      
      <p class = "gray2 tag_p">계정 삭제</p>
      <div><hr></div>
      <a class = "gray" id = "drop">탈퇴</a>
       <br><br>
      </div>
   </div>
</div>

<!-- 이름 변경 -->
<div class="modal" id="myModal4">
    <div class="modal-dialog">
      <div class="modal-content">
         <br><br>
         <h1 class = "tagh1">이름 변경</h1>
         <p class=tag_p>현재 이름  <div id = "userName">${userName}</div></p>
         <p class=tag_p>새로운 이름</p>
         <input type = "text" class = "form-control" id = "newName"> 
         <br>
         <div id = "buttons">
         <button type="submit" class="btn btn-dark">변경</button>
         <button type="reset" class="btn btn-outline-dark" data-dismiss="modal">뒤로 가기</button>
        </div>
      </div>
    </div>
  </div>
 
 <!-- 이메일 변경 --> 
  <div class="modal" id="myModal5">
    <div class="modal-dialog">
      <div class="modal-content">
         <br><br>
         <h1 class = "tagh1">이메일 변경</h1>
         <p class=tag_p>새로운 메일</p>
         <input type = "text" class = "form-control" id = "newEmail">
         <span id=emailCheck>&nbsp;</span>
         <br>
         <div id = "buttons">
         <button type="submit" class="btn btn-dark">변경</button>
         <button type="reset" class="btn btn-outline-dark" data-dismiss="modal">뒤로 가기</button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 비밀번호 변경 -->
  <div class="modal" id="myModal6">
    <div class="modal-dialog">
      <div class="modal-content">
         <br><br>
         <h1 class = "tagh1">비밀번호 변경</h1>
         <p class=tag_p>현재 비밀번호</p>
         <input type = "password" id = "userPass" class = "form-control">
         <span id = "newuserPass">&nbsp;</span> 
         <br> 
         <p class=tag_p>새로운 비밀번호</p>
         <input type = "password" id = "newPass" class = "form-control">
         <span id = "newnewPass">&nbsp;</span> 
         <br>
         <p class=tag_p>비밀번호 확인</p>
         <input type = "password" id = "newPassCheck" class = "form-control">
         <span id = "newnewPassCheck">&nbsp;</span>
         <br><br> 
         <div id = "buttons">
         <button type="submit" class="btn btn-dark">변경</button>
         <button type="reset" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>
  
  
  <!-- 연령대 변경 -->
    <div class="modal" id="myModal7">
    <div class="modal-dialog">
      <div class="modal-content">
         <br><br>
         <h1 class = "tagh1">연령대 변경</h1>
         <select id=newAge>
            <option value=10>10대</option>
            <option value=20>20대</option>
            <option value=30>30대</option>
            <option value=40>40대</option>
            <option value=50>50대</option>
            <option value=60>60대</option>
            <option value=ext>기타연령</option>
         </select><br> 
         <br>
         <div id = "buttons">
         <button type="submit" class="btn btn-dark">변경</button>
         <button type="reset" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>
</body>