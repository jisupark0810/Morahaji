package net.users.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class IdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDAO dao = new UserDAO();
		System.out.println("id = " + request.getParameter("userId"));
		int result = dao.isID(request.getParameter("userId"));
		response.getWriter().append(Integer.toString(result));

		return null;
	}

}
