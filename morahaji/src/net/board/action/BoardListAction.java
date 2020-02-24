package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.BoardRankingBean;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		List<BoardBean> boardlist = new ArrayList<BoardBean>();
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		int page = 1;
		int limit = 7;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		String state = request.getParameter("state");
		System.out.println(state);
		
		//ranking
		List<List<BoardRankingBean>> ranking = new ArrayList<List<BoardRankingBean>>();
		int rankingsize = 5;
		int rankingpage = 3;
		ranking = boarddao.getRanking(rankingsize, rankingpage);
		request.setAttribute("ranking", ranking);

		
		if (state == null) {
			int listcount = boarddao.getListCount();
			boardlist = boarddao.getBoardList(page, limit);
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
			request.setAttribute("boardlist", boardlist);
			request.setAttribute("limit", limit);
			ActionForward forward = new ActionForward();
			forward.setPath("board/qna_board_list.jsp");
			return forward;
		} else if (state.equals("mine")) {
			System.out.println("여기까지");
			int listcount = boarddao.getMyListCount(Integer.parseInt(session.getAttribute("userKey").toString()));
			boardlist = boarddao.getMyBoardList(page, limit,
					Integer.parseInt(session.getAttribute("userKey").toString()));
			int maxpage = (listcount + limit - 1) / limit;
			int startpage = ((page - 1) / 10) * 10 + 1;
			int endpage = startpage + 10 - 1;
			if (endpage > maxpage)
				endpage = maxpage;
			System.out.println("여기까지");
			JsonObject object = new JsonObject();
			object.addProperty("page", page);
			object.addProperty("maxpage", maxpage);
			object.addProperty("startpage", startpage);
			object.addProperty("endpage", endpage);
			object.addProperty("listcount", listcount);
			object.addProperty("limit", limit);
			JsonArray je = new Gson().toJsonTree(boardlist).getAsJsonArray();
			object.add("boardlist", je);
			Gson gson = new Gson();
			System.out.println("여기까지");
			String json = gson.toJson(object);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().append(json);
			return null;
		} else {
			int listcount = boarddao.getListCount();
			boardlist = boarddao.getBoardList(page, limit);
			int maxpage = (listcount + limit - 1) / limit;
			int startpage = ((page - 1) / 10) * 10 + 1;
			int endpage = startpage + 10 - 1;
			if (endpage > maxpage)
				endpage = maxpage;
			JsonObject object = new JsonObject();
			object.addProperty("page", page);
			object.addProperty("maxpage", maxpage);
			object.addProperty("startpage", startpage);
			object.addProperty("endpage", endpage);
			object.addProperty("listcount", listcount);
			object.addProperty("limit", limit);
			JsonArray je = new Gson().toJsonTree(boardlist).getAsJsonArray();
			object.add("boardlist", je);
			Gson gson = new Gson();
			String json = gson.toJson(object);
			response.getWriter().append(json);
			return null;
		}
	}
}
