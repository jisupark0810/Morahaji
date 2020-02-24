function addCount(wordKey, countType){
		$.ajax({
			type : "post",
			url : "countAdd.action",
			data : {
				"postKey" : wordKey,
				"countType" : countType,
				"postType" : "word"
			},
			success : function(resp) {
				var wordKeyVal = wordKey.split('_')[1];
				if(countType == 'like'){
					var count = $('[id*=likenumber_'+wordKeyVal+']').html() * 1;
			        $('[id=likenumber_'+wordKeyVal+']').html(count+1);
				}else if(countType == 'hate'){
					var count = $('[id*=dislikenumber_'+wordKeyVal+']').html() * 1;
					$('[id=dislikenumber_'+wordKeyVal+']').html(count+1);
			        }
				if(resp == "error")
					location.href = "error/error.jsp";
				},
			error : function(request, status, error) {
				location.href = "error/error.jsp";
			}
		}) // end ajax
	}
function removeCount(wordKey, countType){
		$.ajax({
			type : "post",
			url : "countRemove.action",
			data : {
				"postKey" : wordKey,
				"countType" : countType,
				"postType" : 'word'
			},
			success : function(resp) {
				var wordKeyVal = wordKey.split('_')[1];
				if(countType == 'like'){
					var count = $('[id*=likenumber_'+wordKeyVal+']').html() * 1;
			        $('[id=likenumber_'+wordKeyVal+']').html(count-1);
				}else if(countType == 'hate'){
					var count = $('[id*=dislikenumber_'+wordKeyVal+']').html() * 1;
					$('[id=dislikenumber_'+wordKeyVal+']').html(count-1);
			        }
	            $('#bmkword').trigger("click");
	            },
			error : function(request, status, error) {
				alert("error : " + error);
			}
		}) // end ajax
	}
function go(page) {
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
function ajax(data) {
	console.log(data)
	str = "";

	$
			.ajax({
				type : "post",
				url : "mybookmarklist.wd",
				data : data, // 응답받은 data 값
				dataType : "json",
				cache : false,
				success : function(data) {
					//모달
					$("#wordboxAppend").on('click','.yellow',
							function() {
						$(this).toggleClass('yellow');
						var wordKey=$(this).attr('id');
					console.log('wordkey1='+wordKey);
					removeCount(wordKey, 'bookmark');
					
		});
					$("#wordboxAppend").on('click','.thumbsup',
					function() {// 좋아요 클릭
						var wordKey = $(this).attr("id");
						 console.log('wordkey:'+wordKey);
						if($(this).hasClass('selectedBlue')){//좋아요가 되어있으면
							$(this).removeClass('selectedBlue');
							removeCount(wordKey, 'like');
						}else{//좋아요 안되어있음
							removeCount(wordKey, 'hate');
							addCount(wordKey, 'like');
							$(this).addClass('selectedBlue');
						}
					});
					$("#wordboxAppend").on('click','.thumbsdown',
							function() {// 싫어요 클릭
						var wordKey = $(this).attr("id");
						 console.log('wordkey:'+wordKey);
						if($(this).hasClass('selectedBlue')){ //싫어요 되어있으면
							$(this).removeClass('selectedBlue');
							removeCount(wordKey, 'hate');
						}else{//싫어요 안되어있음
							 console.log('wordkey:'+wordKey);
							removeCount(wordKey, 'like');
							addCount(wordKey, 'hate');
							$(this).addClass('selectedBlue');
						}
						});
					//모달 끝
					
					
					$("table").find("font").text("글 개수:" + data.listcount);

					if (data.listcount > 0) { // 총 갯수가 1개이상인 경우
						$("#wordboxAppend").empty();
						
						var num = data.listcount - (data.page - 1) * data.limit;
						console.log("개수=" + num)

						str = "";
						
						$(data.boardlist)
								.each(
										function(index, item) {
											str += "<div class='row wordbox' id='wordbox_"+item.WORD_KEY+"'>"
											str += "<div class='col' id='wordbox_col_"+item.WORD_KEY+"'>"
											+ "<span class='word_date'>"+ item.WORD_DATE_CHAR + "<i class='fa fa-bookmark fa-2x fa-pull-right bookmark yellow' id='bookmark_"+item.WORD_KEY+"'></i></span><br>"		
											
											+"<a href='main.index?keyword="+item.WORD_TITLE+"'>"
											+ "<span class='word_title'>"
													+ item.WORD_TITLE
													+ "</span></a>";
													

											str += "<div class='word_content' id='word_content_"+item.WORD_KEY+"'><p>"
													//+ "<b>뜻: </b>"
													+ item.WORD_CONTENT
													+ "</p><p><b class='Ex'>Ex. </b>"
													+ item.WORD_EXSENTENCE
													+ "</p><p>";
													//+ "<b>업데이트날짜: </b>"
													
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
											str +="<p><b class='By'>By. </b>"
											+ item.WRITER_NAME
											+ "</p><div id='likeorhate'>";
											if(item.LIKE==true){//좋아요 되어있다.
											str+="<span><i class='fa fa-thumbs-up fa-2x like thumbsup selectedBlue' id='thumbsup_"+item.WORD_KEY+"'></i> "+item.LIKECOUNT;
											}
											else{//좋아요 안돼있다
												str+="<span><i class='fa fa-thumbs-up fa-2x like thumbsup' id='thumbsup_"+item.WORD_KEY+"'></i> "+item.LIKECOUNT;	
											}
											if(item.HATE==true){ //싫어요 되어있다
												str+="&nbsp;&nbsp;<i class='fa fa-thumbs-down fa-2x dislike thumbsdown selectedBlue' id='thumbsdown_"+item.WORD_KEY+"'></i> "+item.HATECOUNT+"</span>";
											}
											else{	//안돼있다
												str+="&nbsp;&nbsp;<i class='fa fa-thumbs-down fa-2x dislike thumbsdown' id='thumbsdown_"+item.WORD_KEY+"'></i> "+item.HATECOUNT+"</span>";	
											}	
											
											
											
											str+= "</div></div></div></div>";
											console.log(str);
										})// each end

						$('#wordboxAppend').append(str);// table 완성
						 
						$('#pempty2').empty(); // 페이징 처리
						str = "";
						digit = '◀&nbsp;'
						href = "";
						if (data.page > 1) {
							href = 'href=javascript:go(' + (data.page - 1)
									+ ')';
						}
						setPaging(href, digit);

						for (var i = data.startpage; i <= data.endpage; i++) {
							digit = i;
							href = "";
							if (i != data.page) {
								href = 'href=javascript:go(' + i + ')';
							}
							setPaging(href, digit);
						}

						digit = '▶&nbsp;';
						href = "";
						if (data.page < data.maxpage) {
							href = 'href=javascript:go(' + (data.page + 1)
									+ ')';
						}
						setPaging(href, digit);

						$('#pempty2').append(str)
					}// if(data.listcount > 0) end
					else {
						$("#wordboxAppend").remove();
						$('#pempty2').empty();
						nowrite = "";
						nowrite += "<div class='center'>";
						nowrite += "<font size=5>북마크한 글이 없어요 😓</font>";
						nowrite += "</div>";
						$("#pempty2").append(nowrite);
					}
				},// success end
				error : function() {
					console.log('에러')
				}
			
			})// ajax

	
}// function ajax end

$(function() {
	go(1);
	
	}
)
