package net.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.USERS;
import net.users.db.UserDAO;

public class SearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserDAO udao = new UserDAO();
		ActionForward forward = new ActionForward();

		int page = 1;
		int limit = 10;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("넘어온 페이지=" + page);

		List<USERS> list = null;

		int listcount = 0;
		int index = -1;
		String search_word = "";

		if (request.getParameter("search_word") == null || request.getParameter("search_word").equals("")) {
			listcount = udao.getListCount();
			list = udao.getList(page, limit);
		} else {
			index = Integer.parseInt(request.getParameter("search_field"));
			String[] search_field = new String[] { "USER_ID", "USER_NAME", "USER_EMAIL", "USER_AGERANGE",
					"USER_STATUS" };

			search_word = request.getParameter("search_word");
			listcount = udao.getListCount(search_field[index], search_word);

			list = udao.getList(search_field[index], search_word, page, limit);
		}

		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("최대 페이지=" + maxpage);

		int startpage = ((page - 1) / 10) * 10 + 1;
		System.out.println("시작 페이지=" + startpage);

		int endpage = startpage + 10 - 1;
		System.out.println("마지막 페이지=" + endpage);

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

		forward.setPath("admin/member_list.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
