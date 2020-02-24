package net.word.action;

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
import net.word.db.WORD;
import net.word.db.WordDAO;

public class MyWordList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WordDAO boarddao = new WordDAO();
		List<WORD> boardlist = new ArrayList<WORD>();
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=utf-8");
		int page = 1;
		int limit = 3;
		if (request.getParameter("page") != null) {
			System.out.println("page값이 null아님");
			page = Integer.parseInt(request.getParameter("page"));
			
		}System.out.println("넘어온 페이지="+page);
		
		if(request.getParameter("limit")!=null) {
			limit=Integer.parseInt(request.getParameter("limit"));
		}
		System.out.println("넘어온 limit="+limit);
		
		String state = request.getParameter("state");
		System.out.println("state="+state);
		int listcount = boarddao.getmywordListCount(Integer.parseInt(session.getAttribute("userKey").toString()));
		System.out.println("listcount"+listcount);
		boardlist = boarddao.getmywordList(page, limit, Integer.parseInt(session.getAttribute("userKey").toString()));
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
		System.out.println(json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append(json);
		return null;
	}

}
