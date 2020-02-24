<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
* {
	font-family: "맑은 고딕";
}

@media ( max-width : 800px) {
	.rightcard {
		display: none;
	}
}

.word {
	background: #4185E9;
	color: white;
	width: 250px;
}

.word:hover {
	opacity: 0.8;
	color: white;
}

.mt-0 {
	font-size: 16px;
	font-weight: bold;
}

.lead {
	font-size: 16px;
}

.BOARDDATE {
	font-size: 14px;
}

.card-header {
	font-size: 20px;
	font-weight: bold;
}

.submitbtn {
	float: right;
	width: 80px;
	margin-right: 0;
}

.replyfont {
	color: #ADB5BD;
	cursor: pointer;
}

.replyfont:hover {
	color: #4185E9;
}

.fa-2x {
	font-size: 1.5em;
}

.setting {
	color: #ADB5BD;
	cursor: pointer;
}

.setting:hover {
	color: #343A40;
}

.settingmenu {
	background: #F1F3F4;
	float: right;
}

#settingbar {
	width: 150px;
	position: absolute;
	right: -60px;
	z-index: 1;
}

#settingbarboard {
	width: 150px;
	position: absolute;
	right: 47px;
	top: 25px;
	z-index: 1;
}

.cancelbtn {
	margin-right: 10px;
}

.replyBox {
	margin-bottom: 10px;
}

#modalbackground {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, .3);
	z-index: 10;
}

.btngroup2 {
	position: relative;
	text-align: center
}

.like {
	color: #ADB5BD;
}

.like:hover {
	color: #4F689A;
}

.likeOncolor>i {
	color: #4F689A;
}

.likeBox {
	display: inline-block;
}

.likeDiv {
	display: inline-block;
}

#replydate {
	float: right
}

.replywrite {
	margin-right: 15px;
}

.replyeditsubmit {
	margin-right: 15px;
}

.card-img-top {
	width: 60%;
}

.forgif {
	text-align: center;
}

.repluser {
	color: blue;
	font-style: italic;
}

.reply_to_user {
	color: blue;
}

.replydate {
	color: #595959;
}

.replyusername {
	font-size: 18px;
}

.report {
	color: #ADB5BD;
}

.red {
	color: #FE8F80;
}

.time1, .ad1 {
	display: block;
}

.time2, .time3, .ad2, .ad3 {
	display: none;
}

.best_add {
	color: #a6a6a6; cursor : pointer;
	text-align: center;
	font-size: 10px;
	cursor: pointer;
}

.t {
	text-decoration: none;
	color: black;
}

.rankingLi {
	list-style: none;
	margin: 0;
	padding: 0;
}

.rankingLi>li {
	padding-left: 10%;
}

#rank {
	color: #a6a6a6;
	text-decoration:
}

#rankingTitle {
	text-align: center;
}
.min_height{
min-height: 850px;
}

.replyedit{

cursor: pointer;
}
.bluereply{
color: #007bff;
}

</style>
<body>
	<input type="hidden" name="boardkey" id="boardkey" value="${boardkey }">
	<input type="hidden" name="loginUserkey" id="loginUserkey"
		value="${loginUserkey }">
	<div class="container min_height">
		<div class="row">

			<!-- Post Content Column -->
			<div class="col-sm-8">

				<!-- Title -->
				<h4 class="mt-4">${boarddata.BOARD_TITLE}</h4>
				<c:if test="${loginUserkey == boarddata.USER_KEY ||loginUserkey==1}">
					<div class="modifydelete">
						<i class="fa fa-cog fa-2x fa-pull-right setting "></i>
						<div id="settingbarboard" class="menu1" style="display: none">
							<ul class="nav flex-column settingmenu">
								<li class="nav-item"><a class="nav-link" href="#"
									id="boardmodifybtn" class="edit">수정</a></li>
								<li class="nav-item"><a class="nav-link" href="#"
									id="boarddeletebtn" class="delete" data-toggle="modal"
									data-target="#deletemodal">삭제</a></li>
							</ul>
						</div>
					</div>
				</c:if>
					<div class="report">
					<input type="hidden" value="${loginUserkey}">
					<c:if test="${!empty loginUserkey}">
						<c:if test="${boarddata.REPORT}">
							<i
								class="fa fa-exclamation-triangle fa-2x fa-pull-right report red"
								id="report"></i>
						</c:if>
						<c:if test="${!boarddata.REPORT}">
							<i class="fa fa-exclamation-triangle fa-2x fa-pull-right report"
								id="report"></i>
						</c:if>
					</c:if>
					</div>
				<div id="modalbackground"></div>
				<!-- Author -->
				<p class="lead">
					by <a href="#">${boarddata.USER_NAME}</a>
				</p>

				<hr>

				<!-- Date/Time -->
				<p class="BOARDDATE">${boarddata.BOARD_DATE}에등록</p>

				<hr>

				<!-- Post Content -->
				<p>${boarddata.BOARD_CONTENT }</p>

				<hr>

				<c:if test="${boarddata.BOARD_GIF != null}">
					<!-- GIF 영역 시작 -->
					<div class="forgif">
						<img class="card-img-top" src="${boarddata.BOARD_GIF}"
							id="BOARD_gif_${boarddata.BOARD_KEY}" alt="GIF">
						<!-- GIF 영역 끝 -->
					</div>
					<hr>
				</c:if>
				<div class="row">
					<div class="col-sm-4"></div>
					<div class="col-md-4">
						<div class="btngroup2">
							<div class="likeBox">
								<c:if test="${!likeornot}">
									<div class="likeDiv">
										<i class="fa fa-thumbs-up fa-2x like"></i> <span
											class="likenumber" id="${boarddata.BOARD_KEY}">${likecount}</span>
									</div>
								</c:if>
								<c:if test="${likeornot}">
									<div class="likeDiv likeOncolor > i">
										<i class="fa fa-thumbs-up fa-2x like"></i> <span
											class="likenumber" id="${boarddata.BOARD_KEY}">${likecount}</span>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					<div class="col-sm-4"></div>
				</div>


				<!-- Comments Form -->
				<div class="card my-4">
					<h5 class="card-header">코멘트</h5>
					<div class="card-body">
						<form>
							<div class="form-group">
								<textarea class="form-control" rows="3" id="content"></textarea>
							</div>
							<input type="button" class="btn btn-primary submitbtn" id="write"
								value="등록">
						</form>
					</div>
				</div>
				<div id="comment_box">
					<!-- Single Comment -->
				</div>
			</div>

			<!-- 현재 인기글 시작 -->
			<div class="col-sm-4">
				<div class="card rightcard" style="width: 18rem;">
					<div class="card-body">
						<h5 id=rankingTitle>
							<span class="card-title"><b>👑 RANKING 👑</b></span>
						</h5>
						<br>
						<div id="wrapranking">
							<div class="containerranking">
								<ul class="rankingLi" id="best_search">
									<li class=rankingLi><c:set var="classNum" value="1" /> <c:forEach
											var="r" items="${ranking}">
											<dl class="time${classNum}">
												<c:forEach var="subranking" items="${r}">
													<dd>
														<a class="t"
															href="BoardDetailAction.bo?num=${subranking.BOARD_KEY}"><i
															id=rank>${subranking.RNUM}.</i> ${subranking.BOARD_TITLE}</a>
													</dd>
												</c:forEach>
											</dl>
											<c:set var="classNum" value="${classNum+1}" />
										</c:forEach></li>
									<li class=rankingLi><a class="best_add ad1"
										onClick="javascript:view('0')"> 5 ~ 10위 보기 <img
											src="img/right-arrow.png"></a> <a class="best_add ad2"
										onClick="javascript:view('1')"> 11 ~ 15위 보기 <img
											src="img/right-arrow.png"></a> <a class="best_add ad3"
										onClick="javascript:view('2')"> <img
											src="img/left-arrow.png"> 1 ~ 5위 보기
									</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 현재 인기글 끝 -->


		</div>
	</div>
	<div class="modal" id="deletemodal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Alert</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<!--x부분 data-dismiss는 모달 종료할때 쓰는것 modal을 종료시키겠다. -->
				</div>

				<!-- Modal body -->
				<div class="modal-body">정말로 삭제하시겠습니까?</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<form id=deletemodalForm action="delete.bo" method="post">
						<input type=hidden name=keyvalue value="" id=deletekey> <input
							type=hidden name=typevalue value="" id=deletetype> <input
							type=hidden name=re_lev value="" id=re_lev> <input
							type=hidden name=re_ref value="" id=re_ref> <input
							type=hidden name=re_seq value="" id=re_seq> <input
							type=hidden name=modalboardkey value="${boardkey}"
							id=modalboardkey> <input type="submit"
							class="btn btn-dark" id="deletemodalSubmit" value=삭제>
					<input type="button" class="btn btn-light" data-dismiss="modal"
						value=취소>
					</form>
					
					<!-- 닫기버튼 -->
				</div>
			</div>
		</div>
	</div>
	<script>
		var body = document.body;
		function view(arg) {
			$(".time1, .time2, .time3, .ad1, .ad2, .ad3")
					.css("display", "none");
			if (arg == "0") {
				$(".time2, .ad2").css("display", "block");
				viewcount = 1;
			} else if (arg == "1") {
				$(".time3, .ad3").css("display", "block");
				viewcount = 2;
			} else if (arg == "2") {
				$(".time1, .ad1").css("display", "block");
				viewcount = 0;
			}
		}
		var viewcount = 0;
		var rtcarousel = setInterval(function() {
			view(viewcount)
		}, 5000);

		$("#best_search").mouseenter(function() {
			clearInterval(rtcarousel);
		});

		$("#best_search").mouseleave(function() {
			rtcarousel = setInterval(function() {
				view(viewcount)
			}, 5000);
		});
		var body = document.body;

		   $(document).ready(function() {
		      $('#report').click(function() {
		         if($(this).hasClass('red')){   // 신고 이미 된 상태
		            alert("신고 취소되었습니다.");
		            $(this).toggleClass('red');
		            var boardKey = $("#boardkey").val();
		            removeReport(boardKey);
		         }
		         else{   //신고 되지 않은 상태
		            if("${userKey}" != ""){
		            var reason = "사유";
		            while(reason == "사유"){
		               reason = prompt("신고 사유를 입력해주세요.", "사유");
		               if(!reason) return false;
		            }
		            $(this).toggleClass('red');
		            var boardKey = $("#boardkey").val();
		            addReport(boardKey, reason);
		            } else {
		               location.href = "login.net";
		            }
		         }
		      })
		      
		      function addReport(boardKey, reason){
		         $.ajax({
		            type : "post",
		            url : "reportAdd.action",
		            data : {
		               "boardKey" : boardKey,
		               "reason" : reason,
		               "postType" : "board"
		            },
		            success : function(resp) {
		               console.log("Report success");
		               },
		            error : function(request, status, error) {
		               alert("error : " + error);
		            }
		         }) // end ajax
		      }
		      function removeReport(boardKey){
		         $.ajax({
		            type : "post",
		            url : "reportRemove.action",
		            data : {
		               "postKey" : boardKey,
		               "postType" : "board"
		            },
		            success : function(resp) {
		               console.log("cancel success");
		                  },
		            error : function(request, status, error) {
		               alert("error : " + error);
		            }
		         }) // end ajax
		      }
		      
		      $(".modifydelete").click(function() {
		         $(this).find('#settingbarboard').toggle();
		      })
		      $("#boarddelete,.close").click(function() {
		         $("#modal-dialog,#modalbackground").toggle();
		      });
		      //댓글 리스트 바로 불러오기
		      getList();
		      
		      //로그인 했을 때만 댓글 등록 가능
		         if(($("#loginUserkey").val().length == 0)){
		             $("#write").attr('disabled', true);
		             $("#write").css( 'background', 'gray' );
		             $("#write").css( 'border', '1px solid gray' );
		             $("#content").attr( 'placeholder', '댓글을 등록 하시려면 로그인하세요' );
		             $("#content").keyup(function(){
		                location.href="login.net"
		             })
		          }else{
		             $("#write").attr('disabled', false);
		             $("#content").val('');
		          }
		      
		      //댓글 등록
		      $("#write").click(function() {
		         content = $("#content").val();
		         $.ajax({
		            type : "post",
		            url : "ReplyAdd.rp",
		            data : {
		               "reply_content" : content,
		               "user_key" : $("#loginUserkey").val(),
		               "board_key" : $("#boardkey").val()
		            },
		            dataType : "json",
		            success : function(result) {
		               $("#content").val('');
		               if (result == 1) {
		                  getList();
		               }
		            }
		         })
		      })
		      
		      //댓글의 댓글 아이콘 클릭했을 때 textarea 삽입
		      $("#comment_box").on('click','.replyBox',function() {
		            output='';
		            output +='<form>';
		            output +='<div class="form-group">';
		            output +='<span class="repluser">@'+$(this).prev().val()+'</span><br>';
		            output +='<input type="hidden" class="toreplyuserkey" value="'+$(this).prev().val()+'">';
		            output +='<textarea class="form-control" rows="3" id="replycontent"></textarea>';
		            output +='</div>';
		            output +='<input type="button" class="btn btn-light cancelbtn replycancel pull-right" value="취소">';
		            output +='<input type="button" class="btn btn-primary submitbtn replywrite pull-right" value="등록">';
		            output +='</form>';
		            $(this).next().empty();
		            $(this).next().append(output);
		      });
		      
		      //댓글의 댓글 등록
		      $("#comment_box").on('click','.replywrite',function() {
		         //reply_to_user = $(this).prev().prev().children().next().next().next().next().val();
		         replycontent = $(this).prev().prev().children().next().next().next().val();
		         reply_re_ref = $(this).parent().parent().next().val();
		         reply_re_seq = $(this).parent().parent().next().next().val();
		         reply_re_lev = $(this).parent().parent().next().next().next().val();
		         $.ajax({
		            type : "post",
		            url : "ReplyReplyAdd.rp",
		            data : {
		               "reply_content" : replycontent,
		               "user_key" : $("#loginUserkey").val(),
		               "board_key" : $("#boardkey").val(),
		               "reply_re_ref" : reply_re_ref,
		               "reply_re_seq" : reply_re_seq,
		               "reply_re_lev" : reply_re_lev
		               //"reply_to_user" : reply_to_user
		            },
		            dataType : "json",
		            success : function(result) {
		               $("#content").val('');
		               if (result == 1) {
		                  getList();
		               }
		            }
		         })
		      });
		      
		      //댓글에 댓글등록 취소 버튼
		      $("#comment_box").on('click','.replycancel',function() {
		         $(this).parent().parent().empty();
		      });

		      //톱니아이콘 클릭시 수정, 삭제 버튼 토글
		      $("#comment_box").on('click','.menuBox2',function() {
		         $(this).find('#settingbar').toggle();
		      });
		      
		      //댓글수정 아이콘 클릭했을 때 textarea 삽입
		      $("#comment_box").on('click','.replyedit',function() {
		         output='';
		         output +='<form>';
		         output +='<div class="form-group">';
		         output +='<span class="repluser"></span><br>';
		         output +='<textarea class="form-control" rows="3" id="replyeditcontent"></textarea>';
		         output +='</div>';
		         output +='<input type="button" class="btn btn-light cancelbtn pull-right replyeditcancel" value="취소">';
		         output +='<input type="button" class="btn btn-primary submitbtn pull-right replyeditsubmit" value="수정">';
		         output +='</form>';
		         $(this).parent().parent().parent().parent().next().next().next().next().empty();
		         $(this).parent().parent().parent().parent().next().next().next().next().append(output);
		      });
		      
		      //댓글 수정
		      $("#comment_box").on('click','.replyeditsubmit',function() {
		         replycontent = $("#replyeditcontent").val();
		         reply_key = $(this).parent().parent().prev().prev().prev().val();
		         $.ajax({
		            type : "post",
		            url : "ReplyUpdate.rp",
		            data : {
		               "reply_content" : replycontent,
		               "reply_key" : reply_key
		            },
		            dataType : "json",
		            success : function(result) {
		               $("#content").val('');
		               if (result == 1) {
		                  getList();
		               }
		            }
		         })
		      });
		      
		      //댓글 수정 취소 버튼
		      $("#comment_box").on('click','.replyeditcancel',function() {
		         $(this).parent().parent().empty();
		      });
		      
		      //댓글 삭제
		      $("#comment_box").on('click','.replydelete',function() {
		         $("#deletetype").val("reply");
		         var keyvalue = $(this).parent().parent().parent().parent().next().val();
		         $("#deletekey").val(keyvalue);
		         var re_ref=$(this).parent().parent().parent().parent().next().next().next().next().next().val();
		         var re_seq=$(this).parent().parent().parent().parent().next().next().next().next().next().next().val();
		         var re_lev=$(this).parent().parent().parent().parent().next().next().next().next().next().next().next().val();
		         $("#re_ref").val(re_ref);
		         $("#re_seq").val(re_seq);
		         $("#re_lev").val(re_lev);
		      });
		      
		      //게시글 삭제 버튼 클릭 했을 때
		      $("#boarddeletebtn").click(function() {
		         $("#deletetype").val("board");
		      });
		      
		    //게시글 수정 버튼 클릭 했을 때
	            $("#boardmodifybtn").click(function() {
	               location.href="BoardModify.bo?num="+$("#boardkey").val();
	            });
		    
		      //좋아요===============================================
		      $(".likeBox").ready(function() {
		         $(".likeDiv").click(function() {   // 좋아요
		            var boarddKey = $("#boardkey").val(); 
		            if($(this).hasClass("likeOncolor > i")){   // 좋아요 취소
		               $(this).removeClass("likeOncolor > i");
		               removeCount(boarddKey, 'like');
		            } else {   // 좋아요 안함
		               if($(this).next().hasClass("likeOncolor > i")){ // 싫어요 -> 좋아요
		                  $(this).next().removeClass("likeOncolor > i");
		                  $(this).toggleClass("likeOncolor > i");
		                  removeCount(boarddKey, 'hate');
		                  addCount(boarddKey, 'like');
		               }else { // 좋아요
		                  $(this).toggleClass("likeOncolor > i");
		                  addCount(boarddKey, 'like');
		               }
		            }
		         })
		      })
		      
		      //좋아요 수 증가===============================================
		      function addCount(boarddKey, countType){
		         $.ajax({
		            type : "post",
		            url : "countAdd.action",
		            data : {
		               "postKey" : boarddKey,
		               "countType" : countType,
		               "postType" : "board"
		            },
		            success : function(resp) {
		               if(countType == 'like'){
		                  var count = $('[id='+boarddKey+']').html() * 1;
		                    $('[id='+boarddKey+']').html(count+1);
		               }
		               },
		            error : function(request, status, error) {
		               alert("error : " + error);
		            }
		         }) // end ajax
		      }
		      
		      //좋아요 수 감소===============================================
		      function removeCount(boarddKey, countType){
		         $.ajax({
		            type : "post",
		            url : "countRemove.action",
		            data : {
		               "postKey" : boarddKey,
		               "countType" : countType,
		               "postType" : "board"
		            },
		            success : function(resp) {
		               if(countType == 'like'){
		                  var count = $('[id='+boarddKey+']').html() * 1;
		                    $('[id='+boarddKey+']').html(count-1);
		               }
		                  },
		            error : function(request, status, error) {
		               alert("error : " + error);
		            }
		         }) // end ajax
		      }
		      
		      //댓글 리스트 ajax
		      function getList() {
		         $.ajax({
		            type : "post",
		            url : "ReplyList.rp",
		            data : {
		               "board_key" : $("#boardkey").val()
		            },
		            dataType : "json",
		            success : function(rdata) {
		               if (rdata.length > 0) {
		                  $("#comment_box").empty();
		                  $("#content").text('');
		                  output='';
		                  $(rdata).each(function(){
		                     output +='<div class="media mb-4">';
		                     output +='<img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="thumbnail">';
		                     output +='<div class="media-body">';
		                     output +='<div><span class="mt-0 replyusername">'+this.user_name+'</span><span class="replydate pull-right">'+this.reply_date+'</span></div>';
		                     if(this.origin_user_name != ""){
		                        output +='<span class="reply_to_user"><b>@'+this.origin_user_name+'&nbsp;&nbsp;&nbsp;</b></span>';
		                     }
		                     output +=this.reply_content;
		                     //로그인한 유저키==댓글 유저키 일 때 수정, 삭제 보이기
		                     if($("#loginUserkey").val()==this.user_key||$("#loginUserkey").val()==1){
		                        output +='<div class="menuBox2">';
		                        output +='<i class="fa fa-cog fa-2x fa-pull-right setting " id="settings"></i>';
		                        output +='<div id="settingbar" class="menu1" style="display: none">';
		                        output +='<ul class="nav flex-column settingmenu">';
		                        if($("#loginUserkey").val()==this.user_key){
		                        output +='<li class="nav-item"><a class="nav-link replyedit" class="edit"><span class="bluereply">수정</span></a></li>';
		                        }
		                        output +='<li class="nav-item"><a class="nav-link replydelete" href="#" class="delete" data-toggle="modal" data-target="#deletemodal">삭제</a></li>';
		                        output +='</ul></div></div>';
		                     }
		                     output +='<input type="hidden" name="reply_key" id="reply_key" value="'+this.reply_key+'">';
		                     output +='<input type="hidden" name="user_key" id="user_key" value="'+this.user_name+'">';
		                     if($("#loginUserkey").val().length != 0){
		                        output +='<div class="replyBox">';
		                        output +='<i class="fa fa-reply fa-2x fa-pull-right replyfont"></i>';
		                        output +='</div>';
		                     }
		                     output +='<div class="comrep">';
		                     output +='</div>';
		                     output +='<input type="hidden" name="reply_re_ref" id="reply_re_ref" value="'+this.reply_re_ref+'">';
		                     output +='<input type="hidden" name="reply_re_seq" id="reply_re_seq" value="'+this.reply_re_seq+'">';
		                     output +='<input type="hidden" name="reply_re_lev" id="reply_re_lev" value="'+this.reply_re_lev+'">';
		                     output +='</div></div>';
		                     
		                  });
		                  $("#comment_box").append(output);
		               }
		            }
		         })
		      }
		   })
		   </script>
</body>
</html>