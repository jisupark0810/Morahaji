<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<style>
.table td{
font-size:14px;}
.table{
margin-bottom:100px;
}
table caption {
   color: #343A40;
   font-size: 20px; caption-side : top;
   text-align: center;
   caption-side: top;
}
.back{
text-align:left;
margin-left:20px;
}
.title{text-align:center;margin-bottom:30px;}
.table{text-align:center;}
.abc{
min-height: 850px;}
</style>
<script>
   
</script>
</head>
<body>
<div class="abc">
   <div class="container">
      <div class="row">
         <div class="col-md">
            <a href="member_list.admin" class="back">리스트로 돌아가기</a>
            <h4 class="title">회원 상세 정보</h4>
            <c:set var="m" value="${memberinfo}" />
            <div class="container">
               <table class="table">
                  
                  <tbody>
                     <tr>
                        <td><b>아이디</b></td>
                        <td>${m.USER_ID}</td>
                     </tr>
                     <tr>
                        <td><b>비밀번호</td>
                        <td>${m.USER_PASSWORD}</td>
                     </tr>
                     <tr>
                        <td><b>이름</b></td>
                        <td>${m.USER_NAME}</td>
                     </tr>
                     <tr>
                        <td><b>이메일 주소</b></td>
                        <td>${m.USER_EMAIL}</td>
                     </tr>
                     <tr>
                        <td><b>연령대</b></td>
                        <td>${m.USER_AGERANGE}대</td>
                     </tr>
                     <tr>
                        <td><b>회원 상태</b></td>
                        <c:if test="${m.USER_STATUS==1}">
                           <td>회원</td>
                        </c:if>
                        <c:if test="${m.USER_STATUS==0}">
                           <td>탈퇴</td>
                        </c:if>
                     </tr>
                  </tbody>
               </table>
            </div>
         </div>
      </div>
   </div>
   </div>
</body>