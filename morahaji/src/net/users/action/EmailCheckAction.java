package net.users.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class EmailCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest reqiest, HttpServletResponse response) throws Exception {
		UserDAO dao = new UserDAO();
		System.out.println("email = " + reqiest.getParameter("userEmail"));
		int result = dao.isEmail(reqiest.getParameter("userEmail"));
		response.getWriter().append(Integer.toString(result));

		return null;
	}

}
