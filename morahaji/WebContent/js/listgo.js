//수정 (11/30) 그냥 덮어쓰세요 
function go(page,myall) {
	var data="";
	if (myall){
		var data = "state=mine&page=" + page;
		ajax(data);
	}else {
		var data = "state=all&page=" + page;
		ajax(data);
	}
}

function setPaging(href, digit) {
	output += "<li class=page-item>";
	gray = "";
	if (href == "") {
		gray = "gray";
	}
	anchor = "<a class='page-link " + gray + "'" + href + ">" + digit
			+ "</a></li>";
	output += anchor;
}
function ajax(data) {
	console.log(data)
	output = "";

	$.ajax({
		type : "post",
		url : "BoardList.bo",
		data : data, // 응답받은 data 값
		dataType : "json",
		cache : false,
		success : function(data) {
			$("#viewcount").val(data.limit);
			$("table").find("font").text("글 개수:" + data.listcount);

			if (data.listcount > 0) { // 총 갯수가 1개이상인 경우
				$("tbody").remove();
				var num = data.listcount - (data.page - 1) * data.limit;
				console.log(num)
				output = "<tbody>";
				$(data.boardlist).each(
						function(index, item) {
							output += '<tr><td><div class="media">';
							output += '<div class="media-body"><span class="media-meta pull-right">';
							output += item.BOARD_DATE+'</span><span class="pull-right name fordown">글쓴이: '+item.USER_KEY+'</span>';
							output += '<h4 class="title">';
							output += '<a href="BoardDetailAction.bo?num='+item.BOARD_KEY+'">'+item.BOARD_TITLE;
							output += '</a>';
							output += '</h4><div id="forsummary"><p class="summary">조회수 : '+item.BOARD_READCOUNT+' · 댓글수 :'+item.BOARD_REPLYCOUNT+' · 추천수 : '+item.BOARD_LIKECOUNT+'</p></div>';
							output += '</div></div></td></tr>';
						})// each end
				output += "</tbody>"
				
				$('table').append(output)// table 완성

				$('#pempty').empty(); // 페이징 처리
				output="";
				digit = '이전&nbsp;'
				href = "";
				if (data.page > 1) {
					href = 'href=javascript:go(' + (data.page - 1) + ')';
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

				digit = '다음&nbsp;';
				href = "";
				if (data.page < data.maxpage) {
					href = 'href=javascript:go(' + (data.page + 1) + ')';
				}
				setPaging(href, digit);

				$('#pempty').append(output)
			}// if(data.listcount > 0) end
			else {
				$("tbody").remove();
				$('#pempty').empty();
				nowrite="";
				nowrite+="<div class='center'>";
				nowrite+="<font size=5>등록된 글이 없습니다.</font>";
				nowrite+="</div>";
				$("#pempty").append(nowrite);
			}
		},// success end
		error : function() {
			location.href = "error/error.jsp";
		}
	})// ajax
}// function ajax end

$(document).ready(function(){
	//내가 쓴글 보기/전체 글보기 체크박스 클릭
	$("#box-1").change(function() {
		var myall=true;
		if($("#box-1").is(":checked")==true){
			myall=true;
			go(1,myall);
		}else{
			myall=false;
			go(1,myall);
		}
	})
})