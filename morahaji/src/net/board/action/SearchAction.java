package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class SearchAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("utf-8");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		BoardDAO mdao = new BoardDAO();
		int page = 1;
		int limit = 7;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		List<BoardBean> list = null;
		int listcount = 0;
		int index = -1;
		String search_word = "";
		index = Integer.parseInt(request.getParameter("search_field"));
		String[] search_field = new String[] { "board_title", "board_content", "title+content", "user_name" };
		search_word = request.getParameter("search_word");
		listcount = mdao.getSearchListCount(search_field[index], search_word);
		list = mdao.getSearchBoardList(search_field[index], search_word, page, limit);
		int maxpage = (listcount + limit - 1) / limit;
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage)
			endpage = maxpage;
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listcount", listcount);
		request.setAttribute("totallist", list);
		request.setAttribute("search_field", index);
		request.setAttribute("search_word", search_word);
		forward.setRedirect(false);
		forward.setPath("board/qna_board_search_list.jsp");
		return forward;
	}

}
