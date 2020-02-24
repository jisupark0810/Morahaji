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
				output = "<tbody>";
				$(data.boardlist).each(
						function(index, item) {
							output += '<tr><td><div class="media">';
							output += '<div class="media-body"><span class="media-meta pull-right">';
							output += item.BOARD_DATE
									+ '</span>';
							output += '<h4 class="title">';
							output += '<a href="BoardDetailAction.bo?num='
									+ item.BOARD_KEY
									+ '">'
									+ item.BOARD_TITLE;
							output += '</a><span class="pull-right name">'
									+ item.USER_KEY + '</span>';
							output += '</h4><!-- <p class="summary">추천수 [10] | 댓글 [20]</p> -->';
							output += '</div></div></td></tr>';
						})// each end
				output += "</tbody>"
				$('table').append(output)// table 완성

				$('ul').empty(); // 페이징 처리
				output = "";

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

				$('.pagination').append(output)
			}// if(data.listcount > 0) end
			else {
				$(".container table").remove();
				$(".center-block").remove();
				$(".container").append("<font size=5>등록된 글이 없습니다.</font>");
			}
		},// success end
		error : function() {
			location.href = "error/error.jsp";
		}
	})// ajax
}// function ajax end

$(document).ready(function(){
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
	$("#search").click(function() {
		
	})
	
	$("#write").click(function() {
		location.href = "BoardWrite.bo";
	})
	
})