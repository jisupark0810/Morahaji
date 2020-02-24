function deleteWord(wordKey){
		                     $.ajax({
		                        type : "post",
		                        url : "deleteWord.wd",
		                        data : {"wordKey" : wordKey},
		                        success : function(){location.reload();},
		                        error : function(request, status, error) {
		                           alert("error : " + error);
		                        },      
		                  })
		                  }
function menubox(modalWordKey){
	var id = '#settingbar_'+modalWordKey;
	console.log("modalWordKey : "+modalWordKey+ " / id : " + id);
	$(id).toggle();
}

function go_myword(page) {
	var data = "";
	data = "limit="+3+"&state=ajax&page="+page;
	ajax(data);
}

function setPaging(href, digit) {
	str += "<li class=page-item>";
	gray = "";
	if (href == "") {
		gray = "gray";
	}
	anchor = "<a class='page-link " + gray + "'" + href + ">" + digit
			+ "</a></li>";
	str += anchor;
}
function goReg(){
	location.href='registerWord.wd';
}
function ajax(data) {
	console.log(data)
	str = "";

	$
			.ajax({
				type : "post",
				url : "mywordlist.wd",
				data : data, // 응답받은 data 값
				dataType : "json",
				cache : false,
				success : function(data) {
					///모달
					$("#wordboxAppend").on('click','.deletesubmit',
							function() {
								var result = confirm('정말로 삭제하시겠습니까?');
								if (result) { //yes 
									var sub=$(this).attr("id").split("_");
									var wordKey=sub[1];
								console.log(wordKey);
								deleteWord(wordKey);

								} else {
									//no 
								}
							});
					////모달 끝
					
					
					$("table").find("font").text("글 개수:" + data.listcount);

					if (data.listcount > 0) { // 총 갯수가 1개이상인 경우
						$("#wordboxAppend").empty();
						
						var num = data.listcount - (data.page - 1) * data.limit;
						console.log("개수=" + num)

						str = "";
						
						$(data.boardlist)
								.each(
										function(index, item) {
											str += "<div class='row wordbox' id='wordbox_"+ item.WORD_KEY 	+ "'>"
											str += "<div class='col' id='wordbox_col_"+ item.WORD_KEY 	+ "'>"
											+ "<span class='word_date'>"+ item.WORD_DATE_CHAR + "</span><br>"	
											+"<a href='main.index?keyword="+item.WORD_TITLE+"'>"
											+ "<span class='word_title'>"
													+ item.WORD_TITLE
													+ "</span></a>"
													+ "<div class='menuBox' id='menuBox_"+ item.WORD_KEY 	+ "'>"
													+ "<i class='setting fa fa-2x fa-cog fa-pull-right' onclick=menubox("+ item.WORD_KEY + ")></i>"
													+ "<div id='settingbar_"+ item.WORD_KEY 	+ "' class='menu1 settingbar_' style='display: none'>"
													+ "<ul class='nav flex-column settingmenu'>"
													+ "<li class='nav-item'><a class='nav-link edit' href='modifyWord.wd?wordKey="
													+ item.WORD_KEY + "'>수정</a></li>"
													+ "<li class='nav-item'><a class='nav-link delete deletesubmit' href='#' id='delete_"
													+ item.WORD_KEY + "'>삭제</a></li>"
													+ "</ul></div></div>";

											str += "<div class='word_content' id='word_content_"+ item.WORD_KEY 	+ "'><p>"
													//+ "<b>뜻: </b>"
													+ item.WORD_CONTENT
													+ "</p><p><b class='Ex'>Ex. </b>"
													+ item.WORD_EXSENTENCE
													+ "</p><p>";
													//+ "<b>업데이트날짜: </b>"
													//+ item.WORD_DATE + "<br>";
											if (item.WORD_HASHTAG != null) {
												for (i = 0; i < item.WORD_HASHTAG.length; i++)
													str += "<span><a href='main.index?tag="+item.WORD_HASHTAG[i]+"' class='word_tag'>#"
															+ item.WORD_HASHTAG[i]
															+ "</a>&nbsp;&nbsp;</span>";
											}str +="</p>";
											if (item.WORD_GIF != null) {
												str += "<div><img src='"
														+ item.WORD_GIF
														+ "' class='wordGif' id='wordGif_"+ item.WORD_KEY 	+ "'></div>";
											}
											str += "<span><i class='fa fa-thumbs-up fa-2x like' title='내가 등록한 단어는 좋아요 못해요'></i> "+item.LIKECOUNT
												+"&nbsp;&nbsp;<i class='fa fa-thumbs-down fa-2x dislike' title='내가 등록한 단어는 싫어요 못해요'></i> "+item.HATECOUNT+"</span>"
												+ "</div></div></div>";
											console.log(str);
										})// each end

						$('#wordboxAppend').append(str);// table 완성
						 
						$('#pempty').empty(); // 페이징 처리
						str = "";
						digit = '◀&nbsp;'
						href = "";
						if (data.page > 1) {
							href = 'href=javascript:go_myword(' + (data.page - 1)
									+ ')';
						}
						setPaging(href, digit);

						for (var i = data.startpage; i <= data.endpage; i++) {
							digit = i;
							href = "";
							if (i != data.page) {
								href = 'href=javascript:go_myword(' + i + ')';
							}
							setPaging(href, digit);
						}

						digit = '▶&nbsp;';
						href = "";
						if (data.page < data.maxpage) {
							href = 'href=javascript:go_myword(' + (data.page + 1)
									+ ')';
						}
						setPaging(href, digit);

						$('#pempty').append(str)
					}// if(data.listcount > 0) end
					else {
						$("#wordboxAppend").remove();
						$('#pempty').empty();
						nowrite = "";
						nowrite += "<div class='center'>";
						nowrite += "<font size=5>등록한 글이 없어요 😓<br>" +
								"<button id='register_hiddenbtn' onclick='goReg()'> Write </button></font>";
						nowrite += "</div>";
						$("#pempty").append(nowrite);
						
					}
				},// success end
				error : function() {
					console.log('에러')
				}
			})// ajax
}// function ajax end

$(function() {
	go_myword(1);
})
