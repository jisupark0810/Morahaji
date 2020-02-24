$(document).ready(function() {
$('#gif_find').on("click", function() {
	if($("#gif_search_text").val() == ""){
		return false;
	}
  $("#results").html("");
  // Beginning API call
  var queryURL = "https://api.giphy.com/v1/gifs/search?";
  var query;
  var params = {
    q: query,
    limit: 20,
    api_key: "5tPjNA4rtJUTdZoN5RMoxFEkV4q7STzb",
    fmt: "json"
  };
if ($("#gif_search_text").val() !== "") {
    query = $("#gif_search_text").val();
  }
  params.q = query;

  if ($(this).hasClass("trending")) {
    queryURL = "https://api.giphy.com/v1/gifs/trending?";
    delete params.q;
  }
  $.ajax({
    url: queryURL + $.param(params),
    method: "GET",
    success: function(r) {
      for (i = 0; i < params.limit; i++) {
        var $img = $("<img>");
        var $div = $("<div>");
        var gifObj = r.data[i];
        var gif = gifObj.images;

        // Image builder object
        $img.attr({
          src: gif.fixed_height.url,
          "data-animate": gif.fixed_height.url,
          "data-still": gif.fixed_height_still.url,
          "data-state": "animate",
          "class" : "gif"
        });
        $div.attr("id", "gif-" + i);
        $div.addClass("gif-box");
        $div.append($img);
        $("#results").append($div);
      }

      $(".gif").on("click", function() {
       /* $('#gif_search_text').val("선택되었습니다."); */
        $("#results").html("");
        $("#results").append("<div class='gif-box'><img src="+$(this).attr("src")+" class='gif' data-state='animate'></div>");
        $('#word_gif').val("");
        $('#word_gif').val($(this).attr("src"));
        $('#board_gif').val("");
        $('#board_gif').val($(this).attr("src"));
      });
    }
  });
});
});
